package com.mmag.bgamescoreboard.ui.screen.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val boardGameRepository: BoardGameRepository,
    private val scoringRepository: ScoringRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<GameListUiState> = MutableStateFlow(GameListUiState())
    val uiState: StateFlow<GameListUiState> get() = _uiState

    init {
        _uiState.update { GameListUiState(status = UiStatus.LOADING) }
        getAllGames()
        getRecordsCount()
    }

    private fun getRecordsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            scoringRepository.getRecordsCount().collect { records ->
                _uiState.update { it.copy(recordsCount = records?.size ?: 0) }
            }
        }
    }

    private fun getAllGames() {
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.getAllBoardGames().collect { games ->
                if (games != null) {
                    _uiState.update { it.copy(status = UiStatus.SUCCESS, data = games) }
                } else {
                    _uiState.update { it.copy(status = UiStatus.EMPTY_RESPONSE) }
                }
            }
        }
    }

    fun deleteGame(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.deleteGame(gameId)
        }
    }
}