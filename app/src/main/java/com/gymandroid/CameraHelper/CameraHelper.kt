package com.example.yogacomponentdemo.CameraHelper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.camera2.CameraCaptureSession
import android.util.Base64
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma: Double) -> Unit

class CameraHelper(context: Context) {
    private var context = context
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null
    private var currentImageTook: String? = null
    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    fun startCamera(lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(context)

        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraProviderFuture.addListener(Runnable {
            Log.w("CameraHelper inside", cameraProviderFuture.toString())

            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview

            //ImageCapture

            imageCapture = ImageCapture.Builder().setTargetResolution(Size(
                468, 640)).build()

            // Select front camera
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()

            try {
                Log.w("Try", "Use ")
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                //Preview Deleted in this part
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, imageCapture
                )

            } catch (exc: Exception) {
                Log.e("Use case binding failed", exc.toString())
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
        var bitmapImage: Bitmap? = null
        Log.e("take image", "in take photo")
        // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            cameraExecutor, object :
                ImageCapture.OnImageCapturedCallback() {

                override fun onCaptureSuccess(image: ImageProxy) {
//                    super.onCaptureSuccess(image)
                    Log.e("take image", image.height.toString() + ' ' + image.width.toString())
                    Log.e("take image format", image.format.toString())
                    val planeProxy = image.planes[0]
                    val buffer: ByteBuffer = planeProxy.buffer
                    buffer.rewind()
                    val bytes = ByteArray(buffer.capacity())
                    buffer.get(bytes)
                    bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    val baos = ByteArrayOutputStream()
                    bitmapImage!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val b: ByteArray = baos.toByteArray()
                    currentImageTook = Base64.encodeToString(b, Base64.DEFAULT)
                    image.close()
                    super.onCaptureSuccess(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("take image", exception.toString())
                }
            })
        Log.e("take image return", "return value")
    }

    private fun BitMapToString(bitmap: Bitmap?): String? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    fun getImage(): String? {

        return currentImageTook
    }

    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getPermissions(): Array<String> {
        return REQUIRED_PERMISSIONS
    }

    fun getPermissionsCode(): Int {
        return REQUEST_CODE_PERMISSIONS
    }

    companion object {
        private const val TAG = "CameraXBasic"
        const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    fun closeCamera() {

        // Shut down our background executor
        cameraExecutor.shutdown()
    }
}