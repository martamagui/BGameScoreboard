package com.mmag.bgamescoreboard.ui.screen.game.game_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.domain.use_cases.game.DeleteGameUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.GetGameDetailsUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.MarkAsFavoriteUseCase
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.game.model.GameUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val deleteGameUseCase: DeleteGameUseCase,
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val markAsFavoriteUseCase: MarkAsFavoriteUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<GameUIState> =
        MutableStateFlow(GameUIState(status = UiStatus.LOADING))
    val uiState: MutableStateFlow<GameUIState> get() = _uiState

    fun getGameDetails(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase.invoke(id).collect { response ->
            _uiState.update {
                GameUIState(
                    status = UiStatus.SUCCESS,
                    data = response
                )
            }
        }
    }

    fun deleteGame(gameId: Int) = viewModelScope.launch {
        deleteGameUseCase.invoke(gameId)
    }

    fun markAsFavourite(gameId: Int, isFavorite: Boolean) = viewModelScope.launch {
        markAsFavoriteUseCase.invoke(gameId, isFavorite)
    }
}