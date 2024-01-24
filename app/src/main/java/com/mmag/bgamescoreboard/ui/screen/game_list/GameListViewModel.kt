package com.mmag.bgamescoreboard.ui.screen.game_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(
    private val boardGameRepository: LocalBoardGameRepository
) : ViewModel() {

    private var _boardGames: MutableStateFlow<List<BoardGame>> = MutableStateFlow(
        listOf(
            BoardGame(0, "Hola"),
            BoardGame(1, "Holaa"),
            BoardGame(2, "Hoaala"),
        )
    )
    val boardGames: StateFlow<List<BoardGame>> get() = _boardGames


    init {
        viewModelScope.launch(Dispatchers.IO) {
            boardGameRepository.getAllBoardGames().collect { games ->
                if (games != null) {
                    _boardGames.update { games }
                }
            }
        }
    }


}