package com.mmag.bgamescoreboard.ui.screen.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.domain.use_cases.game.DeleteGameUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.GetAllGamesUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game_record.GetRecordsCountUseCase
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
    private val getAllGamesUseCase: GetAllGamesUseCase,
    private val deleteGameUseCase: DeleteGameUseCase,
    private val getRecordsCountUseCase: GetRecordsCountUseCase,
) : ViewModel() {

    private var _uiState: MutableStateFlow<GameListUiState> = MutableStateFlow(GameListUiState())
    val uiState: StateFlow<GameListUiState> get() = _uiState

    init {
        _uiState.update { GameListUiState(status = UiStatus.LOADING) }
        getAllGames()
        getRecordsCount()
    }

    private fun getRecordsCount() = viewModelScope.launch {
        getRecordsCountUseCase.invoke().collect { records ->
            _uiState.update { it.copy(recordsCount = records.size) }
        }
    }

    private fun getAllGames() = viewModelScope.launch(Dispatchers.IO) {
        getAllGamesUseCase.invoke().collect { games ->
            if (games != null) {
                _uiState.update { it.copy(status = UiStatus.SUCCESS, data = games) }
            } else {
                _uiState.update { it.copy(status = UiStatus.EMPTY_RESPONSE) }
            }
        }
    }

    fun deleteGame(gameId: Int) = viewModelScope.launch { deleteGameUseCase.invoke(gameId) }
}