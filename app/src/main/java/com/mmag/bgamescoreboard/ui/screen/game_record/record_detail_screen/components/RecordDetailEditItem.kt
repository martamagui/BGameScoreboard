package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecordDetailEditItem(
    playerName: String,
    score: Double,
    modifier: Modifier,
    onValueChange: (Double) -> Unit,
) {

    var scorePoints by rememberSaveable { mutableStateOf(score) }

    Column(modifier) {
        Text(
            text = playerName,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        TextField(
            value = scorePoints.toString(),
            onValueChange = { it ->
                scorePoints = (it.toDoubleOrNull() ?: 0) as Double
                onValueChange(scorePoints)
            },
            label = { playerName },
            modifier = Modifier.fillMaxWidth()
        )
    }

}