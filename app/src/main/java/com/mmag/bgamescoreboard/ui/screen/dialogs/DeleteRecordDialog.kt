package com.mmag.bgamescoreboard.ui.screen.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mmag.bgamescoreboard.R

@Composable
fun DeleteRecordDialog(
    onDismiss: () -> Unit,
    onPositiveAction: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.game_detail_delete_record_dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.game_detail_delete_record_dialog_description))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = { onPositiveAction() }
            ) {
                Text(text = stringResource(id = R.string.game_detail_delete_record_dialog_positive_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(stringResource(id = R.string.game_detail_delete_record_dialog_negative_button))
            }
        }
    )
}