package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.use_cases.scores.GetScoresWithPlayersByCategoryUseCase
import com.mmag.bgamescoreboard.domain.use_cases.scores.UpdateScoreUseCase
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
class RecordDetailEditViewModel @Inject constructor(
    private val scoringRepository: ScoringRepository,
    private val getScoresWithPlayersByCategoryUseCase: GetScoresWithPlayersByCategoryUseCase,
    private val updateScoreUseCase: UpdateScoreUseCase,
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
                        getScoresWithPlayersByCategory(data, category.id)
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

    private fun getScoresWithPlayersByCategory(record: RecordWithCategories, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getScoresWithPlayersByCategoryUseCase.invoke(record, categoryId)
                .collectLatest { data ->
                    val currentScores = uiState.value.scoresByPlayersAndCategories
                    currentScores[categoryId] = data
                    val currentCategories =
                        uiState.value.recordWithCategories?.scoringCategories?.toMutableList()
                    val record = uiState.value.recordWithCategories?.record
                    val currentHashMap = uiState.value.scoresByPlayersAndCategories

                    if (!currentCategories.isNullOrEmpty() && record != null) {
                        _uiState.update {
                            it.copy(
                                recordWithCategories = RecordWithCategories(record, currentCategories),
                                scoresByPlayersAndCategories = currentHashMap
                            )
                        }
                    }
                }
        }
    }

    fun updateScore(score: Score) {
        viewModelScope.launch(Dispatchers.IO) {
            updateScoreUseCase.invoke(score)
        }
    }

}