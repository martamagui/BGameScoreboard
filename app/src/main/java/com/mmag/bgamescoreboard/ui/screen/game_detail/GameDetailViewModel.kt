package com.mmag.bgamescoreboard.ui.screen.game_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val boardGameRepository: LocalBoardGameRepository
) : ViewModel() {
    private var _uiSatate: MutableStateFlow<GameDetailUIState> =
        MutableStateFlow(GameDetailUIState())
    val uiState: MutableStateFlow<GameDetailUIState> get() = _uiSatate

    fun getGameDetails(id:Int){
        _uiSatate.update { it.copy(status = UiStatus.LOADING) }
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.getBoardGames(id).collect{response->
                Log.d("RESPONSE", "$response")
                if(response!= null){
                    _uiSatate.update { GameDetailUIState(status = UiStatus.SUCCESS, data = response) }
                }
            }
        }
    }

    fun deleteGame(gameId: Int) {
        //TODO borrar juego y registros
    }
}