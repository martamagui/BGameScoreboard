package com.mmag.bgamescoreboard.ui.common

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null,
)

data class SnackBarAction(
    val message: String,
    val action: () -> Unit,
)

object SnackBarController {
    private val _event = Channel<SnackBarEvent>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _event.send(event)
    }

}