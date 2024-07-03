package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.screen.game_record.players_screen.GameRecordPlayersViewModel
import java.util.Locale


@Composable
fun CategoriesNewCategoryDropDown(
    viewModel: GameRecordPlayersViewModel,
    modifier: Modifier
) {
    var categoryText by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = categoryText, onValueChange = {
                var text = it
                if (text.contains("\n") && text.isNotEmpty()) {
                    text = it.trim().replace("\r", "").replace("\n", "")
                    if (!text.isNullOrEmpty()) {
                        viewModel.saveCategory(text)
                        categoryText = ""
                    }
                } else {
                    categoryText = text.capitalize(Locale.ROOT)
                }
            },
            placeholder = { Text(text = stringResource(id = R.string.categories_screen_category_hint)) },
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        FilledIconButton(
            onClick = {
                viewModel.saveCategory(categoryText)
                categoryText = ""
            },
            enabled = !categoryText.trim().isNullOrEmpty()
        ) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = stringResource(
                    id = R.string.categories_screen_category_description
                ), modifier = Modifier.size(24.dp)
            )
        }
    }
}