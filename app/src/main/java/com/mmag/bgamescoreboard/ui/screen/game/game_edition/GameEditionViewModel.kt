package com.mmag.bgamescoreboard.ui.screen.game.game_edition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.domain.use_cases.game.EditGameUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.GetGameDetailsUseCase
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.game.model.GameUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameEditionViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val editGameUseCase: EditGameUseCase
) : ViewModel() {
    private var _previousDataUiState: MutableStateFlow<GameUIState> =
        MutableStateFlow(GameUIState(status = UiStatus.LOADING))
    val previousDataUiState: MutableStateFlow<GameUIState> get() = _previousDataUiState

    fun getGameDetails(id: Int) = viewModelScope.launch {
        getGameDetailsUseCase.invoke(id).collect { response ->
            _previousDataUiState.update {
                GameUIState(
                    status = UiStatus.SUCCESS,
                    data = response
                )
            }
        }
    }

    fun updateGame(){
        if(previousDataUiState.value.data != null){
            viewModelScope.launch {
                editGameUseCase.invoke(previousDataUiState.value.data!!.game)
            }
        }
    }

}