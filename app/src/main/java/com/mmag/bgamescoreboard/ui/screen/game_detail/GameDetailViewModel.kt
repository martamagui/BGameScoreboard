package com.mmag.bgamescoreboard.ui.screen.game_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val boardGameRepository: BoardGameRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<GameDetailUIState> =
        MutableStateFlow(GameDetailUIState())
    val uiState: MutableStateFlow<GameDetailUIState> get() = _uiState

    fun getGameDetails(id: Int) {
        _uiState.update { it.copy(status = UiStatus.LOADING) }
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.getBoardGame(id).collect { response ->
                Log.d("RESPONSE", "$response")
                if (response != null) {
                    _uiState.update {
                        GameDetailUIState(
                            status = UiStatus.SUCCESS,
                            data = response
                        )
                    }
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