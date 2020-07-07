package com.gymandroid.ui.exercise

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gymandroid.R
import kotlinx.android.synthetic.main.activity_gyroscope.*
import kotlinx.android.synthetic.main.fragment_exercise.*

class gyroscope : AppCompatActivity() {

    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyroscope)

        // gyroscope parts start
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        // gyroscope parts end


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

            if (-80< y.toInt() && y.toInt() < -45) {
                textView5?.text = "Position Good !"
            } else {
                textView5?.text = "Position: needs adjust !"
            }
//            is_pos?.text = "Y : " + y.toInt() + " rad/s"
        }
    }


}