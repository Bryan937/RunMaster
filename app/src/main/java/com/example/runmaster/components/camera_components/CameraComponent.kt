package com.example.runmaster.components.camera_components

import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.runmaster.R
import com.example.runmaster.constants.CommonConstants
import com.example.runmaster.constants.CommonConstants.LARGE_LOGO_SIZE
import com.example.runmaster.constants.CommonConstants.LARGE_PADDING
import com.example.runmaster.constants.CommonConstants.NULL_DP
import com.example.runmaster.constants.CommonConstants.ROW_HEIGHT
import com.example.runmaster.constants.CommonConstants.X_NORMAL_FONT_SIZE
import com.example.runmaster.services.ImageService


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPreview(
    modifier: Modifier,
    sheetScaffoldState: BottomSheetScaffoldState,
    onOpenPreview: (ImageBitmap) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    BottomSheetScaffold(
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = NULL_DP,
        sheetContent = {
            Box(
                modifier = modifier
            )
        }) { padding ->
        Box(
            modifier = modifier.padding(padding)
        ) {
            CameraPreviewComponent(controller = controller, modifier = modifier)
            Column {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(LARGE_PADDING)
                        .height(ROW_HEIGHT),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom
                ) {

                    IconButton(onClick = {
                        onCancel()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = stringResource(id = R.string.cancel),
                            modifier = Modifier.size(96.dp)
                        )
                    }

                    IconButton(onClick = {
                        ImageService.takePhoto(
                            context = context,
                            controller = controller,
                            onPhotoTaken = { imgBitmap -> imageBitmap = imgBitmap },
                            onPhotoPreviewOpen = onOpenPreview
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = stringResource(id = R.string.take_photo),
                            modifier = Modifier.size(LARGE_LOGO_SIZE)
                        )

                    }

                    IconButton(onClick = {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else CameraSelector.DEFAULT_BACK_CAMERA
                    }) {
                        Icon(
                            imageVector = Icons.Default.Cameraswitch,
                            contentDescription = stringResource(id = R.string.switch_camera),
                            modifier = Modifier.size(LARGE_LOGO_SIZE)
                        )

                    }

                }
            }

        }
    }
}

@Composable
fun CameraPreviewComponent(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

@Composable
fun PhotoPreview(
    image: ImageBitmap,
    modifier: Modifier = Modifier,
    onRetake: () -> Unit,
    onUse: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable(enabled = false) {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(bitmap = image, contentDescription = stringResource(id = R.string.taken_photo))
            Spacer(modifier = Modifier.size(CommonConstants.LARGE_SPACER_SIZE))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LARGE_PADDING),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.re_take),
                    modifier = Modifier.clickable {
                        onRetake()
                    },
                    fontSize = X_NORMAL_FONT_SIZE,
                    fontWeight = Bold
                )
                Text(
                    text = stringResource(id = R.string.use),
                    modifier = Modifier.clickable {
                        onUse()
                    },
                    fontSize = X_NORMAL_FONT_SIZE
                )
            }
        }
    }
}
