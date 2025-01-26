package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteRecordDialog
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components.RecordDetailContent
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components.RecordToolbar
import kotlinx.coroutines.delay


@Composable
fun RecordDetailScreen(
    recordId: Int,
    navController: NavController,
    viewModel: RecordDetailViewModel = hiltViewModel<RecordDetailViewModel>()
) {
    LaunchedEffect(rememberCoroutineScope()) {
        viewModel.getCategories(recordId)
    }
    var shouldShowDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var wasRecordDeleted by rememberSaveable {
        mutableStateOf(false)
    }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            RecordToolbar(state, { shouldShowDeleteDialog = true }, navController)
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
                    UiStatus.SUCCESS -> RecordDetailContent(
                        viewModel
                    )

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


