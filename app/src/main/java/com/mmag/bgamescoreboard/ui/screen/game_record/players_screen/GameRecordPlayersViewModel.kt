package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameRecordPlayersViewModel @Inject constructor(
    private val playerRepository: LocalPlayerRepository
) : ViewModel() {


    private var _uiState: MutableStateFlow<GameRecordsPlayersUiState> =
        MutableStateFlow(GameRecordsPlayersUiState())
    val uiState: StateFlow<GameRecordsPlayersUiState> get() = _uiState

    init {
        playerRepository.getSavedPlayers().onEach { players ->
            _uiState.update {
                GameRecordsPlayersUiState(status = UiStatus.SUCCESS, data = players)
            }
        }.launchIn(viewModelScope)
    }

    fun addDeletePlayer(player: Player) {
        val newList = uiState.value.selectedPlayers.toMutableList()
        if (uiState.value.selectedPlayers.contains(player)) {
            newList.remove(player)
        } else {
            newList.add(player)
        }
        _uiState.update {
            it.copy(selectedPlayers = newList)
        }
    }

    fun savePlayer(userName: String) {
        val filteredList = uiState.value.data.filter { player ->
            player.name.lowercase(Locale.ROOT) == userName.lowercase(Locale.ROOT)
        }
        if (filteredList.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                playerRepository.savePlayer(userName)
            }
        }
    }
}
