package com.mmag.bgamescoreboard.ui.screen.game_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R

@Composable
fun GameDetailNotFound(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.game_detail_no_game_found_title),
        modifier = modifier.padding(
            vertical = 32.dp,
            horizontal = 24.dp
        ),
        textAlign = TextAlign.Center
    )
}
