package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.utils.capitalizeFirstLetter

@Composable
fun CategoriesItemCategory(item: ScoringCategory, modifier: Modifier) {
    OutlinedCard(
        modifier = modifier
    ) {
        Text(
            text = item.categoryName.capitalizeFirstLetter(),
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        )
    }
}