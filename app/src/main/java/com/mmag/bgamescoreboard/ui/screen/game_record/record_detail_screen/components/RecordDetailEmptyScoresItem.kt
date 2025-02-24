package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R

@Composable
fun RecordDetailEmptyScoresItem(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.record_detail_category_added_later_than_record),
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp). testTag("RecordDetailEmptyScoresItem"),
    )
}