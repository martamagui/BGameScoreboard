package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.domain.use_cases.categories.GetGameCategoriesUseCase
import com.mmag.bgamescoreboard.domain.use_cases.categories.SaveCategoryUseCase
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getGameCategoriesUseCase: GetGameCategoriesUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase,
) : ViewModel() {

    var gameId: Int? = null

    private var _categoriesUiState: MutableStateFlow<CategoriesUiState> =
        MutableStateFlow(CategoriesUiState())
    val categoriesUiState: MutableStateFlow<CategoriesUiState> get() = _categoriesUiState


    fun getCategories(gameId: Int) {
        this.gameId = gameId
        viewModelScope.launch {
            getGameCategoriesUseCase.invoke(gameId).collect { data ->
                _categoriesUiState.update {
                    CategoriesUiState(status = UiStatus.SUCCESS, data = data)
                }
            }
        }
    }

    fun saveCategory(categoryText: String) = viewModelScope.launch {
        saveCategoryUseCase.invoke(gameId!!, categoryText)
    }
}