package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.components.CategoriesItemCategory
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.components.CategoriesNewCategoryDropDown
import com.mmag.bgamescoreboard.ui.screen.game_record.players_screen.GameRecordPlayersViewModel
import com.mmag.bgamescoreboard.ui.theme.Typography


@Composable
fun CategoriesScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameRecordPlayersViewModel = hiltViewModel<GameRecordPlayersViewModel>(
        navController.getBackStackEntry(BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), 1))
    ).apply { getCategories(gameId) },
) {
    val uiState by viewModel.categoriesUiState.collectAsState()

    Scaffold(
        topBar = {
            BGSToolbar(
                title = stringResource(id = R.string.categories_screen_title)
            ) { navController.popBackStack() }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp),

            ) {
            if (uiState.status == UiStatus.LOADING) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )
            }
            CategoriesContent(uiState, gameId.toString(), navController) { text ->
                viewModel.saveCategory(text)
            }

        }
    }

}

@Composable
private fun CategoriesContent(
    uiState: CategoriesUiState,
    gameId: String,
    navController: NavController,
    saveCategory: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CategoryAdditionContent() { text -> saveCategory(text) }
            Divider(Modifier.padding(vertical = 12.dp))
            if (uiState.data != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    items(
                        items = uiState.data,
                        key = { item: ScoringCategory ->
                            item.id
                        }) { item ->
                        CategoriesItemCategory(
                            item,
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(
                        BGSConfigRoutes.Builder.newScoreStep(gameId, 3)
                    )
                },
                enabled = uiState.data.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.categories_screen_action_button))
            }
        }
    }
}

@Composable
private fun CategoryAdditionContent(
    saveCategory: (String) -> Unit,
) {
    var isNewCategoryVisible1 by rememberSaveable { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable {
                    isNewCategoryVisible1 = !isNewCategoryVisible1
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.categories_screen_category_hint),
                style = Typography.bodyLarge
            )
            Icon(
                imageVector = if (isNewCategoryVisible1) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                },
                contentDescription = stringResource(
                    id = if (isNewCategoryVisible1) {
                        R.string.categories_screen_category_close_description
                    } else {
                        R.string.categories_screen_category_open_description
                    }
                ), modifier = Modifier.size(32.dp)
            )
        }
        AnimatedVisibility(isNewCategoryVisible1) {
            CategoriesNewCategoryDropDown(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) { text -> saveCategory(text) }
        }
    }
}


