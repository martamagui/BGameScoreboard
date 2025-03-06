package com.mmag.bgamescoreboard.ui.screen.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObtainImageDialog(
    onDismiss: () -> Unit,
    onOpenCamera: () -> Unit,
    onOpenGallery: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.testTag("TakeImageDialog")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 48.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.obtain_image_dialog_title)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onOpenCamera() }) {
                    Row {
                        Text(
                            text = stringResource(R.string.obtain_image_dialog_camera_button),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = stringResource(
                                id = R.string.new_game_camera_icon_description
                            )
                        )
                    }
                }
                Button(onClick = { onOpenGallery() }) {
                    Row {
                        Text(
                            text = stringResource(R.string.obtain_image_dialog_gallery_button),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_image_search),
                            contentDescription = stringResource(
                                id = R.string.obtain_image_dialog_gallery_button
                            )
                        )
                    }
                }
            }
        }
    }
}