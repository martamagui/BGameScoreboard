package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteRecordDialog
import kotlinx.coroutines.delay



@Composable
fun RecordDetailScreen(
    recordId: Int,
    navController: NavController,
    viewModel: RecordDetailViewModel = hiltViewModel<RecordDetailViewModel>().also {
        it.getCategories(recordId)
    }
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    var shouldShowDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var wasRecordDeleted by rememberSaveable {
        mutableStateOf(false)
    }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            RecordToolbar(state, {shouldShowDeleteDialog = true}, navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            if (shouldShowDeleteDialog) {
                DeleteRecordDialog(onDismiss = { shouldShowDeleteDialog = false }) {
                    viewModel.deleteRecord(recordId)
                    wasRecordDeleted = true
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (state.status) {
                    UiStatus.LOADING -> {}
                    UiStatus.SUCCESS -> {
                        RecordDetailContent(tabIndex, state, viewModel)
                    }

                    UiStatus.ERROR -> {}
                    else -> {
                        Text(text = stringResource(id = R.string.record_detail_not_found_message))
                    }
                }
            }
        }
    }
    LaunchedEffect(wasRecordDeleted) {
        if (wasRecordDeleted) {
            delay(200)
            navController.popBackStack()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RecordToolbar(
    state: RecordDetailUiState,
    showDeleteDialog: ()->Unit,
    navController: NavController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                state.recordWithCategories?.record?.date ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { showDeleteDialog()}) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.toolbar_delete_action_description)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.toolbar_back_action_description)
                )
            }
        })
}

@Composable
private fun RecordDetailContent(
    tabIndex: Int,
    state: RecordDetailUiState,
    viewModel: RecordDetailViewModel
) {
    var tabIndex1 = tabIndex
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        item {
            ScrollableTabRow(
                selectedTabIndex = tabIndex1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                state.recordWithCategories?.scoringCategories?.forEachIndexed { index, text ->
                    Tab(
                        selected = tabIndex1 == index,
                        onClick = {
                            tabIndex1 = index
                            viewModel.updateCategorySelected(index)
                        }
                    ) {
                        Text(
                            text = text.categoryName,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        if (state.selectedCategoryIndex != null) {
            val scores = state.scoresByPlayersAndCategories[
                state.recordWithCategories?.scoringCategories?.get(tabIndex1)?.id
            ] ?: listOf()
            items(scores) { score ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
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
        }
    }
}
