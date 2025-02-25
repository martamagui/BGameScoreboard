package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailUiState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RecordToolbar(
    state: RecordDetailUiState,
    showDeleteDialog: () -> Unit,
    enableDisableEditMode: () -> Unit,
    quitEditModeMode: () -> Unit,
    isEditMode: Boolean,
    navController: NavController,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    MediumTopAppBar(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                state.recordWithCategories?.record?.date ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            if (isEditMode) {
                IconButton(onClick = { quitEditModeMode() }) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = stringResource(id = R.string.toolbar_delete_action_description)
                    )
                }
            } else {
                IconButton(onClick = { enableDisableEditMode() }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.toolbar_delete_action_description)
                    )
                }
                IconButton(onClick = { showDeleteDialog() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.toolbar_delete_action_description)
                    )
                }
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