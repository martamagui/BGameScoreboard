package com.mmag.bgamescoreboard.ui.screen.game_detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameDetailScreen(gameId: Int) {
    Scaffold { paddingValues ->
        Text(text = "$gameId", modifier = Modifier.padding(paddingValues).fillMaxWidth())
    }
}