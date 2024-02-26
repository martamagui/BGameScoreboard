package com.mmag.bgamescoreboard.ui.screen.new_game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import com.mmag.bgamescoreboard.ui.model.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val boardGameRepository: LocalBoardGameRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<NewGameUiState> = MutableStateFlow(NewGameUiState())
    val uiState: MutableStateFlow<NewGameUiState> get() = _uiState

    fun saveGame(inputStream: InputStream?, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(status = UiStatus.LOADING) }
            val bitmap = convertStreamToBitmap(inputStream)
            if (bitmap != null) {
                boardGameRepository.addGame(name, bitmap)
                _uiState.update { it.copy(status = UiStatus.SUCCESS, errorMessage = null) }
            } else {
                _uiState.update {
                    NewGameUiState(
                        status = UiStatus.ERROR,
                        R.string.new_game_no_photo_error
                    )
                }
            }
        }
    }

    private fun convertStreamToBitmap(inputStream: InputStream?): Bitmap? {
        try {
            if (inputStream != null) {
                val image = BitmapFactory.decodeStream(inputStream)
                if (image.byteCount <= 2500000) {
                    return image
                } else {
                    var compressedImage = image
                    do {
                        var scaleWidth = compressedImage.width / 2
                        var scaleHeight = compressedImage.height / 2

                        compressedImage =
                            Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, true)
                    } while (compressedImage.byteCount >= 2500000)
                    return compressedImage
                }
            } else {
                _uiState.update {
                    NewGameUiState(
                        status = UiStatus.ERROR,
                        R.string.new_game_no_photo_error
                    )
                }
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update {
                NewGameUiState(
                    status = UiStatus.ERROR,
                    R.string.new_game_no_photo_error
                )
            }
            return null
        }
    }
}