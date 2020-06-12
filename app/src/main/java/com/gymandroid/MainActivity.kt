package com.gymandroid

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise.*
import com.gymandroid.ui.exercise.excerciseListActivity as excerciseListActivity1
import com.gymandroid.ui.exercise.ExercisingActivity

class MainActivity : AppCompatActivity() {

    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        } else {setTheme(R.style.AppTheme);}
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings_appbar_button -> {
                    // handle settings button click
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(R.anim.enter_new_from_right, R.anim.enter_old_from_right)
                    true
                }
                else -> false
            }
        }

        // gyroscope parts start
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)


        // gyroscope parts end
    }

    fun startExercise(view: View) {
        Toast.makeText(this@MainActivity, "Starting Exercise !", Toast.LENGTH_SHORT).show();
        startActivity(Intent(this@MainActivity, excerciseListActivity1::class.java))
    }

    fun startExercisingPage(view: View) {
        Toast.makeText(this@MainActivity, "Exercising Page !", Toast.LENGTH_SHORT).show();
        startActivity(Intent(this@MainActivity, ExercisingActivity::class.java))
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

            //is_pos.setText("hello").toString()
        }
    }
}





