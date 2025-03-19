package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.mapper.toPlayerModel
import com.mmag.bgamescoreboard.domain.mapper.toPlayerWithScore
import com.mmag.bgamescoreboard.domain.model.PlayerModel
import com.mmag.bgamescoreboard.domain.use_cases.categories.GetGameCategoriesUseCase
import com.mmag.bgamescoreboard.domain.use_cases.categories.SaveCategoryUseCase
import com.mmag.bgamescoreboard.domain.use_cases.player.GetMostFrequentPlayersUseCase
import com.mmag.bgamescoreboard.domain.use_cases.player.GetSavedPlayersUseCase
import com.mmag.bgamescoreboard.domain.use_cases.player.SavePlayerUseCase
import com.mmag.bgamescoreboard.ui.model.CategoryWithRecords
import com.mmag.bgamescoreboard.ui.model.PlayerWithScore
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.model.toPlayerWithScore
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.CategoriesUiState
import com.mmag.bgamescoreboard.utils.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameRecordPlayersViewModel @Inject constructor(
    private val scoringRepository: ScoringRepository,
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val savePlayerUseCase: SavePlayerUseCase,
    private val getGameCategoriesUseCase: GetGameCategoriesUseCase,
    private val getSavedPlayersUseCase: GetSavedPlayersUseCase,
    private val getMostFrequentPlayersUseCase: GetMostFrequentPlayersUseCase,
) : ViewModel() {

    var gameId: Int? = null

    private var _playersUIState: MutableStateFlow<GameRecordsPlayersUiState> =
        MutableStateFlow(GameRecordsPlayersUiState())
    val playersUIState: StateFlow<GameRecordsPlayersUiState> get() = _playersUIState

    private var _categoriesUiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val categoriesUiState: MutableStateFlow<CategoriesUiState> get() = _categoriesUiState

    private var _scoreData: MutableList<CategoryWithRecords> = mutableListOf()
    private val scoreData: List<CategoryWithRecords> get() = _scoreData

    init {
        getSavedPlayers()
    }

    //region --- BD ---
    private fun getSavedPlayers() = viewModelScope.launch {
        getSavedPlayersUseCase.invoke().collect { players ->
            val playerModelList = players.map { player ->
                player.toPlayerModel()
            }
            _playersUIState.update {
                GameRecordsPlayersUiState(status = UiStatus.SUCCESS, data = playerModelList)
            }
            getMostFrequentPlayers()
        }
    }

    private fun getMostFrequentPlayers() = viewModelScope.launch {
        val frequentPlayers = getMostFrequentPlayersUseCase.invoke()
        val frequentPlayerIds = frequentPlayers.map { it.playerId }.toSet()
        val updatedList = playersUIState.value.data.map { player ->
            player.copy(isFrequent = player.id in frequentPlayerIds)
        }
        updatedList.sortedBy { it.isFrequent }
        _playersUIState.update {
            it.copy(data = updatedList)
        }
    }

    fun addDeletePlayer(player: PlayerModel) {
        val newList = playersUIState.value.selectedPlayers.toMutableList()
        if (playersUIState.value.selectedPlayers.contains(player)) {
            newList.remove(player)
        } else {
            newList.add(player)
        }
        _playersUIState.update {
            it.copy(selectedPlayers = newList)
        }
    }

    fun savePlayer(userName: String) {
        if (!isPlayerAlreadySaved(userName)) {
            viewModelScope.launch { savePlayerUseCase.invoke(userName) }
        }
    }

    fun getCategories(gameId: Int) {
        this.gameId = gameId
        viewModelScope.launch {
            getGameCategoriesUseCase.invoke(gameId).collect { data ->
                addCategories(data)
                _categoriesUiState.update {
                    CategoriesUiState(status = UiStatus.SUCCESS, data = data)
                }
            }
        }
    }

    fun saveCategory(categoryText: String) = viewModelScope.launch {
        saveCategoryUseCase.invoke(gameId!!, categoryText)
    }

    fun saveScoreRecord(onDoneCallback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recordTitle =
                "${playersUIState.value.selectedPlayers.size} p. - ${getCurrentDate()}"
            val savedRecord: Int? = gameId?.let {
                scoringRepository.addRecord(recordTitle, it).toInt()
            }

            if (savedRecord != null) {
                scoreData.forEach { category ->
                    category.savedScores.forEach { score ->
                        val isNotSaved = scoringRepository.getScoreByRecordIdAndPlayer(
                            savedRecord,
                            score.playerId,
                            category.categoryId
                        ) == null
                        if (isNotSaved) {
                            scoringRepository.addScore(
                                score.playerId,
                                savedRecord,
                                category.categoryId,
                                score.score
                            )
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) { onDoneCallback() }
        }
    }
    //endregion --- BD ---

    //region --- Other ---
    private suspend fun addCategories(categories: List<ScoringCategory>) {
        runBlocking {
            async {
                categories.forEach { category ->
                    _scoreData.add(
                        CategoryWithRecords(
                            category.id,
                            playersUIState.value.selectedPlayers.toPlayerWithScore()
                        )
                    )
                }
            }
        }.await()
        _scoreData.toSet()
    }

    fun updatePlayerScoreValue(category: Int, data: PlayerWithScore) {
        val currentCategoryData = _scoreData.firstOrNull { it.categoryId == category }
        val currentCategoryDataIndex = _scoreData.indexOf(currentCategoryData)
        val userScore =
            currentCategoryData?.savedScores?.firstOrNull { it.playerId == data.playerId }
        if (userScore != null) {
            val index = currentCategoryData.savedScores.indexOf(userScore)
            currentCategoryData.savedScores[index] = data
        }
        if (currentCategoryData != null) {
            _scoreData[currentCategoryDataIndex] = currentCategoryData
        }
    }

    private fun isPlayerAlreadySaved(userName: String): Boolean {
        val filteredList = playersUIState.value.data.filter { player ->
            player.name.lowercase(Locale.ROOT) == userName.lowercase(Locale.ROOT)
        }
        return filteredList.isNotEmpty()
    }
    //endregion --- Other ---

}
