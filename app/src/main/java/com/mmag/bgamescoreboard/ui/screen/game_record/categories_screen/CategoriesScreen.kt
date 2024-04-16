package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.game_record.players_screen.GameRecordPlayersViewModel
import com.mmag.bgamescoreboard.ui.theme.Typography
import java.util.Locale


@Composable
fun CategoriesScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameRecordPlayersViewModel = hiltViewModel<GameRecordPlayersViewModel>(
        navController.getBackStackEntry(BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), 1))
    ).apply { getCategories(gameId) }
) {
    val uiState by viewModel.categoriesUiState.collectAsState()
    var categoryText by rememberSaveable { mutableStateOf("") }
    var isNewCategoryVisible by rememberSaveable { mutableStateOf(false) }

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
            Column(modifier = Modifier.fillMaxWidth()) {
                if (uiState.status == UiStatus.LOADING) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            isNewCategoryVisible = !isNewCategoryVisible
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.categories_screen_category_hint),
                        style = Typography.bodyLarge
                    )
                    Icon(
                        imageVector = if (isNewCategoryVisible) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        contentDescription = stringResource(
                            id = if (isNewCategoryVisible) {
                                R.string.categories_screen_category_close_description
                            } else {
                                R.string.categories_screen_category_open_description
                            }
                        ), modifier = Modifier.size(32.dp)
                    )
                }
                AnimatedVisibility(isNewCategoryVisible) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
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

                Divider(Modifier.padding(vertical = 12.dp))

                if (uiState.data != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),

                        ) {
                        items(uiState.data) { item ->
                            OutlinedCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = item.categoryName.capitalize(Locale.ROOT),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
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
                            BGSConfigRoutes.Builder.newScoreStep(
                                viewModel.gameId.toString(),
                                3
                            )
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

}