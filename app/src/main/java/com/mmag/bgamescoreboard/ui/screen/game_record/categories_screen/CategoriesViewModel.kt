package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepository
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepositoryRoomImpl
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val scoreRepository: LocalScoreRepository
) : ViewModel() {

    var gameId: Int? = null

    private var _uiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val uiState: MutableStateFlow<CategoriesUiState> get() = _uiState

    fun getCategories(gameId: Int) {
        this.gameId = gameId
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.getCategoriesByGameId(gameId).collect { data ->
                _uiState.update {
                    CategoriesUiState(status = UiStatus.SUCCESS, data = data)
                }
            }
        }
    }

    fun saveCategory(categoryText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (gameId != null) {
                scoreRepository.addCategory(gameId!!, categoryText)
            } else {
                _uiState.update {
                    it.copy(
                        status = UiStatus.ERROR,
                        errorMessage = R.string.not_game_found_error
                    )
                }
            }
        }
    }


}