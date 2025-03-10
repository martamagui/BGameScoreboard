package com.mmag.bgamescoreboard.ui.common

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.mmag.bgamescoreboard.R

@Composable
fun SelectGamePhotoComponent(
    modifier: Modifier,
    onClickAction: () -> Unit,
    selectedImage: Uri?,
    previousPicture: Bitmap? = null,
) {
    Card(
        modifier = modifier,
        onClick = {
            onClickAction()
        }
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {

                if (previousPicture != null && selectedImage == null) {
                    Image(
                        bitmap = previousPicture.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = stringResource(
                            id = R.string.new_game_camera_icon_description
                        )
                    )
                }
                if (selectedImage != null) {
                    AsyncImage(
                        model = selectedImage,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = stringResource(
                            id = R.string.new_game_camera_icon_description
                        )
                    )
                    Text(text = stringResource(id = R.string.new_game_camera_title))
                }
            }

        }
    }
}