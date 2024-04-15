package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.ui.model.CategoryWithRecords
import com.mmag.bgamescoreboard.ui.model.PlayerWithScore
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.model.toPlayerWithScore
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameRecordPlayersViewModel @Inject constructor(
    private val playerRepository: LocalPlayerRepository,
    private val scoringRepository: ScoringRepository
) : ViewModel() {
    var gameId: Int? = null

    private var _playersUIState: MutableStateFlow<GameRecordsPlayersUiState> =
        MutableStateFlow(GameRecordsPlayersUiState())
    val playersUIState: StateFlow<GameRecordsPlayersUiState> get() = _playersUIState

    private var _categoriesUiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val categoriesUiState: MutableStateFlow<CategoriesUiState> get() = _categoriesUiState

    private var _scoreData: MutableList<CategoryWithRecords> = mutableListOf()
    val scoreData: List<CategoryWithRecords> get() = _scoreData

    init {
        playerRepository.getSavedPlayers().onEach { players ->
            _playersUIState.update {
                GameRecordsPlayersUiState(status = UiStatus.SUCCESS, data = players)
            }
        }.launchIn(viewModelScope)
    }

    //region --- BD ---
    fun addDeletePlayer(player: Player) {
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
        val filteredList = playersUIState.value.data.filter { player ->
            player.name.lowercase(Locale.ROOT) == userName.lowercase(Locale.ROOT)
        }
        if (filteredList.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                playerRepository.savePlayer(userName)
            }
        }
    }

    fun getCategories(gameId: Int) {
        this.gameId = gameId
        scoringRepository.getCategoriesByGameId(gameId).onEach { data ->
            if (data != null) {
                addCategories(data)
            }
            _categoriesUiState.update {
                CategoriesUiState(status = UiStatus.SUCCESS, data = data)
            }
        }.launchIn(viewModelScope)
    }

    fun saveCategory(categoryText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (gameId != null) {
                scoringRepository.addCategory(gameId!!, categoryText)
            } else {
                _categoriesUiState.update {
                    it.copy(
                        status = UiStatus.ERROR,
                        errorMessage = R.string.not_game_found_error
                    )
                }
            }
        }
    }

    fun saveScoreRecord(onDoneCallback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedRecord: Int? = gameId?.let {
                scoringRepository.addRecord(
                    "Partida a ${playersUIState.value.selectedPlayers.size} jugadores - ${getCurrentDate()}",
                    it
                ).toInt()
            }
            Log.d("Registro guardado", "ID: $savedRecord")

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
    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return formatter.format(time)
    }

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


    //endregion --- Other ---

}
