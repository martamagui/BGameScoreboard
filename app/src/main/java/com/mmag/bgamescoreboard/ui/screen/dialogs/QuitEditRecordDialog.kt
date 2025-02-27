package com.mmag.bgamescoreboard.ui.screen.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.mmag.bgamescoreboard.R


@Composable
fun QuitEditRecordDialog(
    onQuitWithoutSaving: () -> Unit,
    onSaveAndQuit: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.record_detail_quit_edit_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.record_detail_quit_edit_dialog_description))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = { onSaveAndQuit() }
            ) {
                Text(text = stringResource(id = R.string.record_detail_quit_edit_dialog_confirm_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onQuitWithoutSaving() }
            ) {
                Text(stringResource(id = R.string.record_detail_quit_edit_dialog_dismiss_button))
            }
        },
        modifier = Modifier.testTag("QuitEditRecordDialog")
    )
}