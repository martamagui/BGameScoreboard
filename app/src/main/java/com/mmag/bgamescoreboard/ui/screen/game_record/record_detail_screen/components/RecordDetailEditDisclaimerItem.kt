package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R

@Composable
fun RecordDetailEditDisclaimerItem(modifier: Modifier) {
    Card(modifier = modifier) {
        Box(Modifier.fillMaxWidth(). padding(12.dp)) {
            Text(text = stringResource(R.string.record_detail_edit_disclaimer_title))
        }
    }
}