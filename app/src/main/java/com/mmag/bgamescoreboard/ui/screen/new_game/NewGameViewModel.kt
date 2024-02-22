package com.mmag.bgamescoreboard.ui.screen.new_game

import androidx.lifecycle.ViewModel
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val boardGameRepository: LocalBoardGameRepository
) : ViewModel() {
}