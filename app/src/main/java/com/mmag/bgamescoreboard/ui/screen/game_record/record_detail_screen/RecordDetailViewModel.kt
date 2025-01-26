package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.use_cases.DeleteRecordUseCase
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val scoringRepository: ScoringRepository,
    private val deleteRecordUseCase: DeleteRecordUseCase,
) : ViewModel() {

    private var _uiState: MutableStateFlow<RecordDetailUiState> = MutableStateFlow(
        RecordDetailUiState()
    )
    val uiState: StateFlow<RecordDetailUiState> get() = _uiState

    fun getCategories(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoringRepository.getRecordWithCategories(recordId).collectLatest { data ->
                if (data != null) {
                    data.scoringCategories.forEach { category ->
                        getRecordByCategory(recordId, category.id)
                    }
                    _uiState.update {
                        it.copy(
                            recordWithCategories = data,
                            status = UiStatus.SUCCESS
                        )
                    }
                }
            }
        }
    }

    private suspend fun obtainTotalAmountState(
        recordId: Int,
        scoresByPlayersAndCategories: HashMap<Int, List<ScoreWithPlayer>>,
    ) {
        val lastCategory = scoresByPlayersAndCategories[scoresByPlayersAndCategories.keys.last()]
        val totalScoreList = getPlayersScoreForEachCategory(lastCategory, recordId)

        val currentCategories =
            uiState.value.recordWithCategories?.scoringCategories?.toMutableList()
        val record = uiState.value.recordWithCategories?.record
        val currentHashMap = uiState.value.scoresByPlayersAndCategories

        if (!currentCategories.isNullOrEmpty() && record != null) {
            val containsTotal = currentCategories.any { item -> item.categoryName == "TOTAL" }
            if (!containsTotal) {
                addTotalCategory(currentHashMap, totalScoreList, currentCategories)
                currentCategories.sortBy { selector -> selector.id }
            }
            _uiState.update {
                it.copy(
                    recordWithCategories = RecordWithCategories(record, currentCategories),
                    scoresByPlayersAndCategories = currentHashMap
                )
            }
        }
    }

    private suspend fun getPlayersScoreForEachCategory(
        lastCategory: List<ScoreWithPlayer>?,
        recordId: Int,
    ): MutableList<ScoreWithPlayer> {
        val totalScoreList = mutableListOf<ScoreWithPlayer>()
        lastCategory?.forEach { item ->
            val totalScore = scoringRepository.getPlayersScoresFromRecord(recordId, item.player.id)
            totalScoreList.add(getScoreWithPlayer(item, recordId, totalScore))
        }
        return totalScoreList
    }

    private fun addTotalCategory(
        currentHashMap: HashMap<Int, List<ScoreWithPlayer>>,
        totalScoreList: MutableList<ScoreWithPlayer>,
        currentCategories: MutableList<ScoringCategory>,
    ) {
        currentHashMap.put(-1, totalScoreList)
        currentCategories.add(ScoringCategory(-1, currentCategories.first().gameId, "TOTAL"))
    }

    private fun getRecordByCategory(recordId: Int, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoringRepository.getScoresWithPlayersByCategory(recordId, categoryId)
                .collectLatest { data ->
                    val currentScores = uiState.value.scoresByPlayersAndCategories
                    currentScores[categoryId] = data
                    obtainTotalAmountState(recordId, currentScores)
                }
        }
    }

    fun deleteRecord(recordId: Int) = viewModelScope.launch { deleteRecordUseCase.invoke(recordId) }

    private fun getScoreWithPlayer(
        item: ScoreWithPlayer,
        recordId: Int,
        totalScore: Int,
    ) = ScoreWithPlayer(
        Score(
            0, item.player.id,
            recordId,
            0,
            totalScore
        ),
        item.player
    )
}