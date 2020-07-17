package com.gymandroid.ui.exercise

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.*
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.gymandroid.Helper
import com.gymandroid.R
import com.gymandroid.YogaActivity
import kotlinx.android.synthetic.main.activity_gyroscope.*
import java.io.File
import java.util.*
import kotlin.math.absoluteValue

class gyroscope : AppCompatActivity() {

    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null
    lateinit var helper: Helper

    private val TAG = "AndroidCameraApi"
    private var textureView: TextureView? = null
    private var cameraId: String? = null
    protected var cameraDevice: CameraDevice? = null
    protected var cameraCaptureSessions: CameraCaptureSession? = null
    protected var captureRequest: CaptureRequest? = null
    protected var captureRequestBuilder: CaptureRequest.Builder? = null
    private var imageDimension: Size? = null
    private val imageReader: ImageReader? = null
    private val file: File? = null
    private val REQUEST_CAMERA_PERMISSION = 200
    private val mFlashSupported = false
    private val mBackgroundHandler: Handler? = null
    private val mBackgroundThread: HandlerThread? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyroscope)

        // gyroscope parts start
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
//        helper = Helper.getInstance(this)
//        if (!helper.camera.allPermissionsGranted()){
//            ActivityCompat.requestPermissions(this,helper.camera.getPermissions(),
//                helper.camera.getPermissionsCode())
//            Log.e("Main","Cammera not granted")
//
//        }

        // gyroscope parts end

        // camera preview
        textureView = findViewById<View>(R.id.camera) as TextureView
        assert(textureView != null)
        textureView!!.surfaceTextureListener = textureListener

        // camera preview
        start_exercising_btn.setOnClickListener {
            val intent = Intent(this, YogaActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        sensorManager!!.unregisterListener(gyroListener)
    }
    private var gyroListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, acc: Int) {}

        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            if (-85< y.toInt() && y.toInt() < -45) {
                textView5?.text = "Position Good !"
                progressBar.setProgress(100, true)
//                start_exercising_btn.isClickable = true
            } else {
                textView5?.text = "Position: needs adjust !"
                if (y.toInt() < -85) {
                    progressBar.setProgress(((y.toInt() + 245).absoluteValue / 1.6).toInt(), true)
                } else {
                    progressBar.setProgress(((y.toInt() - 115).absoluteValue / 1.6).toInt(), true)
                }

//                start_exercising_btn.isClickable = false
            }
        }
    }

    var textureListener: TextureView.SurfaceTextureListener = object :
        TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            //open your camera here
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            // Transform you image captured size according to the surface width and height
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }
    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened")
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice!!.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice!!.close()
            cameraDevice = null
        }
    }

    protected fun createCameraPreview() {
        try {
            val texture = textureView!!.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension!!.width, imageDimension!!.height)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder!!.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                Arrays.asList(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        //The camera is already closed
                        if (null == cameraDevice) {
                            return
                        }
                        // When the session is ready, we start displaying the preview.
                        cameraCaptureSessions = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Toast.makeText(
                            this@gyroscope,
                            "Configuration change",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val manager =
            getSystemService(Context.CAMERA_SERVICE) as CameraManager
        Log.e(TAG, "is camera open")

        lateinit var cameraId : String
        try {
            cameraId = manager.cameraIdList[1]
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map =
                characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
            imageDimension = map.getOutputSizes(SurfaceTexture::class.java)[0]
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@gyroscope,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CAMERA_PERMISSION
                )
                return
            }
            manager.openCamera(cameraId, stateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        Log.e(TAG, "openCamera X")
    }

    protected fun updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return")
        }
        captureRequestBuilder!!.set(
            CaptureRequest.CONTROL_MODE,
            CameraMetadata.CONTROL_MODE_AUTO
        )
        try {
            cameraCaptureSessions!!.setRepeatingRequest(
                captureRequestBuilder!!.build(),
                null,
                mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (grantResults.isNotEmpty() && grantResults[0] ==
//            PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Permission Accepted", Toast.LENGTH_SHORT).show()
//        }
//        else {
//            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }


}