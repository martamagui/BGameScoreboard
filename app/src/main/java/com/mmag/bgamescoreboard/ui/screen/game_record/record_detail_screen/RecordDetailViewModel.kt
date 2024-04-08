package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepository
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
    private val scoreRepository: LocalScoreRepository,
    private val gameRepository: LocalBoardGameRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<RecordDetailUiState> = MutableStateFlow(
        RecordDetailUiState()
    )
    val uiState: StateFlow<RecordDetailUiState> get() = _uiState

    fun getCategories(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.getRecordWithCategories(recordId).collectLatest { data ->
                if (data != null) {
                    data.scoringCategories.forEach { category ->
                        getRecordByCategory(recordId, category.id)
                    }
                    if (uiState.value.selectedCategoryIndex == null &&
                        !data.scoringCategories.isNullOrEmpty()
                    ) {
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

    fun updateCategorySelected(selectedCategory: Int) {
        _uiState.update {
            it.copy(selectedCategoryIndex = selectedCategory)
        }
    }

    private fun getRecordByCategory(recordId: Int, categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.getScoresWithPlayersByCategory(recordId, categoryId)
                .collectLatest { data ->
                    val currentScores = uiState.value.scoresByPlayersAndCategories
                    currentScores[categoryId] = data
                    _uiState.update { it.copy(scoresByPlayersAndCategories = currentScores) }
                }
        }
    }


}