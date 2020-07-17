package com.gymandroid

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gymandroid.ui.exercise.ExercisingActivity
import com.gymandroid.ui.exercise.gyroscope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise.*
import com.gymandroid.ui.exercise.excerciseListActivity as excerciseListActivity1

class MainActivity : AppCompatActivity() {

    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null

    var textY: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
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
                    overridePendingTransition(
                        R.anim.enter_new_from_right,
                        R.anim.enter_old_from_right
                    )
                    true
                }
                else -> false
            }
        }

//        // gyroscope parts start
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
//
//        // gyroscope parts end
//        clearRecords(this)
//        addRecords(
//            this,
//            ExerciseRecord(
//                0,
//                "Report",
//                98.6f,
//                0.7f,
//                "You exercised 4.8 hours last week. 2.3 hours were spent on sit-ups, 1.9 hours were spent on push-ups, and 0.6 hours were spent on jogging."
//            )
//        )
//        addRecords(this, ExerciseRecord(1592438400, "Sit-ups", 98.6f, 5.7f, "Most sit-ups are correct. Arm positions need to be corrected as your arms are ... most times. [More analysis...]"))
//        addRecords(this, ExerciseRecord(1592418400, "Sit-ups", 98.6f, 2.7f, "Most sit-ups are correct. Arm positions need to be corrected as your arms are ... most times. [More analysis...]"))
//        addRecords(this, ExerciseRecord(1092418400, "Yoga", 98.6f, 1.1f, "Most sit-ups are correct. Arm positions need to be corrected as your arms are ... most times. [More analysis...]"))
    }

    fun startExercise(view: View) {
        makeText(this@MainActivity, "Starting Exercise !", LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, excerciseListActivity1::class.java))
//        startActivity(Intent(this@MainActivity, BackEnd::class.java))
    }

    fun startExercisingPage(view: View) {
        makeText(this@MainActivity, "Exercising Page !", LENGTH_SHORT).show()
        startActivity(Intent(this@MainActivity, ExercisingActivity::class.java))
    }



//    override fun onResume() {
//        super.onResume()
//        sensorManager!!.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        sensorManager!!.unregisterListener(gyroListener)
//    }

//    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


//    private var gyroListener: SensorEventListener = object : SensorEventListener {
//        override fun onAccuracyChanged(sensor: Sensor, acc: Int) {}
//
//        override fun onSensorChanged(event: SensorEvent) {
//            val x = event.values[0]
//            val y = event.values[1]
//            val z = event.values[2]
//
//            if (-80< y.toInt() && y.toInt() < -60) {
//                is_pos?.text = "Position Good !"
//            } else {
//                is_pos?.text = "Position: needs adjust !"
//            }
////            is_pos?.text = "Y : " + y.toInt() + " rad/s"
//        }
//    }
}





