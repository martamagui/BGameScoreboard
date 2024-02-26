package com.mmag.bgamescoreboard.ui.screen.game_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val boardGameRepository: LocalBoardGameRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<GameListUiState> = MutableStateFlow(GameListUiState())
    val uiState: StateFlow<GameListUiState> get() = _uiState

    init {
        _uiState.update { GameListUiState(status = UiStatus.LOADING) }
        getAllGames()
    }

    private fun getAllGames() {
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.getAllBoardGames().onEach { games ->
                if (games != null) {
                    _uiState.update { GameListUiState(status = UiStatus.SUCCESS, data = games) }
                } else {
                    _uiState.update { GameListUiState(status = UiStatus.EMPTY_RESPONSE) }
                }
            }.launchIn(viewModelScope)
        }
    }
}