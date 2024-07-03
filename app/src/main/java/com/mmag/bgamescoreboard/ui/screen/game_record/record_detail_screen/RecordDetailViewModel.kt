package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
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
                    if (uiState.value.selectedCategoryIndex == null &&
                        !data.scoringCategories.isNullOrEmpty()
                    ) {
                        Log.w("DATA", "entra de primeras")
                        _uiState.update {
                            it.copy(
                                recordWithCategories = data,
                                status = UiStatus.SUCCESS,
                                selectedCategoryIndex = 0
                            )
                        }
                    } else {
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
    }

    private suspend fun createTotalAmountScreen(
        recordId: Int,
        scoresByPlayersAndCategories: HashMap<Int, List<ScoreWithPlayer>>
    ) {
        val lastCategory =
            scoresByPlayersAndCategories[scoresByPlayersAndCategories.keys.last()]
        val totalScoreList = mutableListOf<ScoreWithPlayer>()
        lastCategory?.forEach { item ->
            val totalScore = scoringRepository.getPlayersScoresFromRecord(recordId, item.player.id)
            totalScoreList.add(
                ScoreWithPlayer(
                    Score(
                        0, item.player.id,
                        recordId,
                        0,
                        totalScore
                    ),
                    item.player
                )
            )
        }
        val currentCategories =
            uiState.value.recordWithCategories?.scoringCategories?.toMutableList()
        val record = uiState.value.recordWithCategories?.record
        val currentHashMap = uiState.value.scoresByPlayersAndCategories
        if (!currentCategories.isNullOrEmpty() && record != null) {
            currentHashMap.put(-1, totalScoreList)
            currentCategories.removeIf { item -> item.categoryName == "TOTAL" }
            currentCategories.add(ScoringCategory(-1, currentCategories.first().gameId, "TOTAL"))
            currentCategories.sortBy { selector-> selector.id }
            _uiState.update {
                it.copy(
                    recordWithCategories = RecordWithCategories(record, currentCategories),
                    scoresByPlayersAndCategories = currentHashMap
                )
            }
            Log.w("DATA", uiState.value.toString())
        } else {
            Log.w("DATA", "algo nulo")
        }

    }

    fun deleteRecord(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoringRepository.deleteRecordAndScores(recordId)
        }
    }

    fun updateCategorySelected(selectedCategory: Int) {
        _uiState.update {
            it.copy(selectedCategoryIndex = selectedCategory)
        }
    }

    private fun getRecordByCategory(recordId: Int, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoringRepository.getScoresWithPlayersByCategory(recordId, categoryId)
                .collectLatest { data ->
                    val currentScores = uiState.value.scoresByPlayersAndCategories
                    currentScores[categoryId] = data
                    _uiState.update { it.copy(scoresByPlayersAndCategories = currentScores) }
                    createTotalAmountScreen(recordId, currentScores)
                }
        }
    }
}