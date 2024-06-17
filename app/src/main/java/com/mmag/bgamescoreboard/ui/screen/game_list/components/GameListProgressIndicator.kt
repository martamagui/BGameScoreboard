package com.mmag.bgamescoreboard.ui.screen.game_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R

@Composable
fun GameListProgressIndicator(modifier: Modifier) {
    LinearProgressIndicator(modifier = modifier)
    Text(
        text = stringResource(id = R.string.game_list_searching_games_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        textAlign = TextAlign.Center
    )
}