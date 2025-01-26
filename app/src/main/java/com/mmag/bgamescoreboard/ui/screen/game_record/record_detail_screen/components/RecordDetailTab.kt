package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailViewModel

@Composable
fun RecordDetailTab(
    tabIndex: Int,
    index: Int,
    selectTab: (tab: Int) -> Unit,
    text: ScoringCategory
) {
    Tab(
        selected = tabIndex == index,
        onClick = {
            selectTab(index)
        }
    ) {
        Text(
            text = text.categoryName,
            modifier = Modifier.padding(8.dp)
        )
    }
}