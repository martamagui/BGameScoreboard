package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.theme.Typography
import java.util.Locale

@Composable
fun CategoriesScreen(
    gameId: Int,
    navController: NavController,
    viewModel: CategoriesViewModel = hiltViewModel<CategoriesViewModel>().apply {
        getCategories(gameId)
    }
) {
    val uiState by viewModel.uiState.collectAsState()
    var categoryText by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            BGSToolbar(
                title = stringResource(id = R.string.categories_screen_title)
            ) { navController.popBackStack() }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (uiState.status) {
                UiStatus.LOADING -> {}
                UiStatus.SUCCESS -> {
                    if (uiState.data != null && uiState.data.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.categories_screen_description),
                            style = Typography.headlineMedium
                        )

                        TextField(
                            value = categoryText, onValueChange = {
                                it.trim()
                                if (it.contains("\n") && it.isNotEmpty()) {
                                    viewModel.saveCategory(categoryText)
                                    categoryText = ""
                                } else {
                                    categoryText = it.capitalize(Locale.ROOT)
                                }
                            },
                            placeholder = { Text(text = stringResource(id = R.string.categories_screen_category_hint)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        )

                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            items(uiState.data) { item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Text(text = item.categoryName.capitalize(Locale.ROOT))
                                }
                            }
                        }
                    } else {
                    }
                }

                UiStatus.ERROR -> TODO()
                UiStatus.EMPTY_RESPONSE -> TODO()
                UiStatus.UNKNOWN -> TODO()
            }
        }

    }

}