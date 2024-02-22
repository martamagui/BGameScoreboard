package com.mmag.bgamescoreboard.ui.screen.new_game

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import kotlinx.coroutines.selects.select

@Composable
fun NewGameScreen(navController: NavController) {
    var selectedImage by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    var gameName by rememberSaveable { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImage = uri
    }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BGSToolbar(title = stringResource(id = R.string.new_game_title)) {
            navController.popBackStack()
        }
    }) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                NewGamePhotoSelector(modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), onClickAction = {
                    launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                })

                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = gameName,
                    onValueChange = { gameName = it },
                    label = { Text(text = stringResource(id = R.string.new_game_game_name_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

    }

}

@Composable
fun NewGamePhotoSelector(modifier: Modifier, onClickAction: () -> Unit) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
