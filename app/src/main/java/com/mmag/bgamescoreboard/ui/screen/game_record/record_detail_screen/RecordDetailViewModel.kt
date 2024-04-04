package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val scoreRepository: LocalScoreRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<RecordDetailUiState> = MutableStateFlow(
        RecordDetailUiState()
    )
    val uiState: StateFlow<RecordDetailUiState> get() = _uiState

    fun getCategories(recordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.getCategoriesByGameRecord(recordId).collectLatest { data ->
                _uiState.emit(
                    RecordDetailUiState(categoriesList = data, status = UiStatus.SUCCESS)
                )
            }
        }
    }
}