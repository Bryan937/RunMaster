package com.example.runmaster.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream


class ImageService {
    companion object {
        fun encodeImageToBase64(imageBitmap: ImageBitmap?): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap?.asAndroidBitmap()
                ?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

        fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
            val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        fun takePhoto(
            context: Context,
            controller: LifecycleCameraController,
            onPhotoTaken: (ImageBitmap) -> Unit,
            onPhotoPreviewOpen: (ImageBitmap) -> Unit
        ) {

            controller.takePicture(ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        super.onCaptureSuccess(image)

                        val matrix = Matrix().apply {
                            postRotate(image.imageInfo.rotationDegrees.toFloat())
                        }
                        val rotatedBitmap = Bitmap.createBitmap(
                            image.toBitmap(), 0, 0, image.width, image.height, matrix, true
                        )
                        image.use {
                            val imageBitmap = rotatedBitmap.asImageBitmap()
                            onPhotoTaken(imageBitmap)
                            onPhotoPreviewOpen(imageBitmap)
                        }
                    }

                })
        }
    }
}