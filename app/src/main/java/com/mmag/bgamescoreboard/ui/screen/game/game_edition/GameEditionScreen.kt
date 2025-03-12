package com.mmag.bgamescoreboard.ui.screen.game.game_edition

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.common.SelectGamePhotoComponent
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.utils.openAppSettings
import com.mmag.bgamescoreboard.ui.screen.dialogs.CameraPermissionRationaleDialog
import com.mmag.bgamescoreboard.ui.screen.dialogs.ObtainImageDialog
import com.mmag.bgamescoreboard.utils.createTempPictureUri
import com.mmag.bgamescoreboard.utils.toBitmap
import java.io.InputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GameEditionScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameEditionViewModel = hiltViewModel<GameEditionViewModel>().also {
        it.getGameDetails(gameId)
    },
) {
    val context = LocalContext.current
    val previousDataUiState by viewModel.previousDataUiState.collectAsState()

    var isObtainImageDialogOpen by rememberSaveable { mutableStateOf(false) }
    var shouldShowRationale by rememberSaveable { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImage by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImage = uri
            }
        }
    val cameraLauncher: ActivityResultLauncher<Uri> =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                selectedImage = tempPhotoUri
            }
        }
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = { isPermissionGranted ->
            if (isPermissionGranted) {
                tempPhotoUri = context.createTempPictureUri()
                cameraLauncher.launch(tempPhotoUri!!)
            } else {
                shouldShowRationale = true
            }
        })


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BGSToolbar(title = stringResource(id = R.string.edit_game_title)) {
            navController.popBackStack()
        }
    }) { padding ->
        Box(Modifier.padding(padding)) {
            if (previousDataUiState.status == UiStatus.SUCCESS) {
                GameEditionContent(
                    onClickAction = { image, gameName ->
                        viewModel.updateGame(gameName, image)
                        navController.popBackStack()
                    },
                    onSelectImage = {
                        isObtainImageDialogOpen = true
                    },
                    selectedImage = selectedImage,
                    baseGameName = previousDataUiState.data?.game?.name ?: "",
                    previousPicture = previousDataUiState.data?.game?.image
                )
            }

            if (isObtainImageDialogOpen) {
                ObtainImageDialog(
                    onDismiss = { isObtainImageDialogOpen = false },
                    onOpenCamera = {
                        when (cameraPermissionState.status) {
                            is PermissionStatus.Denied -> {
                                if (cameraPermissionState.status.shouldShowRationale) {
                                    isObtainImageDialogOpen = true
                                } else {
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            }

                            PermissionStatus.Granted -> {
                                tempPhotoUri = context.createTempPictureUri()
                                cameraLauncher.launch(tempPhotoUri!!)
                            }
                        }
                        isObtainImageDialogOpen = false
                    },
                    onOpenGallery = {
                        galleryLauncher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                        isObtainImageDialogOpen = false
                    },
                )
            }

            if (shouldShowRationale) {
                CameraPermissionRationaleDialog(
                    onDismiss = { isObtainImageDialogOpen = false },
                    onPositiveAction = {
                        shouldShowRationale = false
                        openAppSettings(context)
                    }
                )
            }
        }
    }
}

@Composable
fun GameEditionContent(
    onClickAction: (Bitmap?, String) -> Unit,
    onSelectImage: () -> Unit,
    selectedImage: Uri?,
    baseGameName: String = "",
    previousPicture: Bitmap?,
) {
    val context = LocalContext.current
    var gameName by rememberSaveable { mutableStateOf(baseGameName) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("GameEditionContent")
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SelectGamePhotoComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                onClickAction = { onSelectImage() },
                selectedImage = selectedImage,
                previousPicture = previousPicture
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
                        val imageStream: InputStream? = selectedImage?.let {
                            context.contentResolver.openInputStream(it)
                        }
                        onClickAction(imageStream?.toBitmap(), gameName)
                    },
                    enabled = (gameName.isNotEmpty()),
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
