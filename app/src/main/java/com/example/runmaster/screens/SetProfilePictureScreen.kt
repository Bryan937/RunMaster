package com.example.runmaster.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.runmaster.R
import com.example.runmaster.components.camera_components.BottomSheetContent
import com.example.runmaster.components.camera_components.CameraPreview
import com.example.runmaster.components.camera_components.PhotoPreview
import com.example.runmaster.components.commom.GradientButtonComponent
import com.example.runmaster.components.commom.ProfileImageComponent
import com.example.runmaster.components.commom.TextComponent
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.CONTINUE
import com.example.runmaster.constants.CommonConstants.LARGE_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.LARGE_SPACER_SIZE
import com.example.runmaster.constants.CommonConstants.LATER
import com.example.runmaster.constants.CommonConstants.NORMAL_FONT_SIZE
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE
import com.example.runmaster.models.sessions.User
import com.example.runmaster.services.ImageService
import com.example.runmaster.utils.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetProfilePicScreen(navController: NavController) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }

        val sheetScaffoldStateState = rememberBottomSheetScaffoldState()
        var isCameraOpen by rememberSaveable {
            mutableStateOf(false)
        }

        var isPhotoPreviewOpen by rememberSaveable {
            mutableStateOf(false)
        }
        var isImageSet by rememberSaveable {
            mutableStateOf(false)
        }
        var text by rememberSaveable {
            mutableStateOf(LATER)
        }
        LaunchedEffect(isImageSet) {
            if (isImageSet) text = CONTINUE
        }

        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri ->
                selectedImageUri = uri
                val inputStream =
                    selectedImageUri?.let { context.contentResolver.openInputStream(it) }
                if (inputStream != null) {
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageBitmap = bitmap.asImageBitmap()
                    val base64Image = ImageService.encodeImageToBase64(imageBitmap)
                    User.instance.setInfoByLabel("Register", base64Image)
                    isImageSet = true
                } else {
                    isImageSet = false
                }
            })


        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                headerComposable(uri = selectedImageUri)
                Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
                Spacer(modifier = Modifier.weight(0.5f))
                GradientButtonComponent(
                    value = stringResource(id = R.string.add_picture),
                    onClick = { isSheetOpen = true })
                Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
                LaterButton(navController, text = text)
                Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
                if (isSheetOpen) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = { isSheetOpen = false }) {
                        BottomSheetContent(onAddClick = {
                            isSheetOpen = false
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

                            )
                        },
                            onTakeClick = {
                                isSheetOpen = false
                                isCameraOpen = true
                            })
                    }
                }

            }
            if (isCameraOpen) {
                CameraPreview(modifier = Modifier.fillMaxSize(),
                    sheetScaffoldState = sheetScaffoldStateState,
                    onOpenPreview = { img ->
                        isCameraOpen = false
                        isPhotoPreviewOpen = true
                        isSheetOpen = false
                        imageBitmap = img
                    },
                    onCancel = {
                        isCameraOpen = false
                        isPhotoPreviewOpen = false
                        isSheetOpen = true
                    })
            }
            if (isPhotoPreviewOpen) {
                PhotoPreview(image = imageBitmap!!, modifier = Modifier.fillMaxSize(), onRetake = {
                    isCameraOpen = true
                    isPhotoPreviewOpen = false
                    isSheetOpen = false
                }, onUse = {
                    val base64Image = ImageService.encodeImageToBase64(imageBitmap)
                    User.instance.setInfoByLabel("Register", base64Image)
                    isCameraOpen = false
                    isPhotoPreviewOpen = false
                    isSheetOpen = false
                    isImageSet = true
                })
            }

        }
    }
}

@Composable
fun headerComposable(uri: Uri?) {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            textValue = stringResource(id = R.string.add_profile_picture),
            textSize = LARGE_FONT_SIZE,
            fontWeightValue = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))
        TextComponent(
            textValue = stringResource(id = R.string.add_profile_picture_para),
            textSize = X_NORMAL_FONT_SIZE
        )
        Spacer(modifier = Modifier.height(LARGE_SPACER_SIZE))

        ProfileImageComponent(uri)
    }
}

@Composable
fun LaterButton(navController: NavController, text: String) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val buttonWidth =
        if (screenWidthDp < CommonConstants.MOBILE_THRESHOLD) CommonConstants.MOBILE_BUTTON_WIDTH else CommonConstants.TABLET_BUTTON_WIDTH
    Button(modifier = Modifier
        .width(buttonWidth)
        .heightIn(CommonConstants.BUTTON_HEIGHT),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurface),
        onClick = {
            if (User.instance.getAuth().currentUser != null) {
                User.instance.updateUserProfilePic { success, message -> null }
            } else {
                User.instance.updateDatabase { session, message -> null }
            }
            navController.navigate(Screen.Home.route)
        }) {
        Text(text = text, fontSize = NORMAL_FONT_SIZE, fontWeight = FontWeight.Bold)
    }
}




