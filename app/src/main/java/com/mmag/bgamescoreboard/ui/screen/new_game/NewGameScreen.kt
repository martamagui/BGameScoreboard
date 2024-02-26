package com.mmag.bgamescoreboard.ui.screen.new_game

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus

@Composable
fun NewGameScreen(
    navController: NavController,
    viewModel: NewGameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var selectedImage by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    var gameName by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedImage = uri
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BGSToolbar(title = stringResource(id = R.string.new_game_title)) {
            navController.popBackStack()
        }
    }) { padding ->
        Box(Modifier.padding(padding)) {
            if (uiState.status == UiStatus.LOADING) {
                LinearProgressIndicator()
            }
            if (uiState.status == UiStatus.SUCCESS) {
                navController.popBackStack()
            }
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NewGamePhotoSelector(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        onClickAction = {
                            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        selectedImage = selectedImage
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    OutlinedTextField(
                        value = gameName,
                        onValueChange = { gameName = it },
                        label = { Text(text = stringResource(id = R.string.new_game_game_name_label)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Button(
                            onClick = {
                                val imageStream =
                                    context.contentResolver.openInputStream(selectedImage!!)
                                viewModel.saveGame(imageStream, gameName)
                            },
                            enabled = (!gameName.isNullOrEmpty() && selectedImage != null),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.new_game_action_button),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(Modifier.height(32.dp))
                    }

                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGamePhotoSelector(
    modifier: Modifier,
    onClickAction: () -> Unit,
    selectedImage: Uri?
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
            if (selectedImage != null) {
                AsyncImage(
                    model = selectedImage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
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
