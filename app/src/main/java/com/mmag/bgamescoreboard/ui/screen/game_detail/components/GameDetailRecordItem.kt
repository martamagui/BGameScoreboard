package com.mmag.bgamescoreboard.ui.screen.game_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailRecordItem(item: GameScoreRecord, modifier: Modifier, goToDetailAction: () -> Unit) {
    Card(modifier = modifier.padding(8.dp), onClick = { goToDetailAction() }) {
        Text(
            text = item.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}