package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer

@Composable
fun RecordDetailItem(score: ScoreWithPlayer, modifier: Modifier) {
    Row(
        modifier = modifier.testTag("RecordDetailItem"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = score.player.name,
            fontSize = 20.sp
        )
        Text(
            text = score.score.scoreAmount.toString(),
            fontSize = 20.sp
        )
    }
}