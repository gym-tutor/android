package com.gymandroid

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gymandroid.Settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

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
                R.id.theme_change->{
                    val menu: Menu = app_bar.menu;
                    val themcontroller: MenuItem = menu.findItem(R.id.theme_change)
                    if (themcontroller.isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        themcontroller.setIcon(R.drawable.night_icon);
                        themcontroller.setChecked(false);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        themcontroller.setChecked(true);
                        themcontroller.setIcon(R.drawable.day_icon);

                    }
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

//        Examples of using updateOrAddRecord:
//        addRecords(this, ExerciseRecord(1092418400, "TEST", 0.1f, 1.1f, "Most sit-ups are correct. Arm positions need to be corrected as your arms are ... most times. [More analysis...]"))
//        updateOrAddRecord(this, ExerciseRecord(1192418400, "TEST", 98.6f, 1.1f, "LOLs. [More analysis...]"))
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





