package com.gymandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import com.gymandroid.ui.exercise.excerciseListActivity as excerciseListActivity1

class MainActivity : AppCompatActivity() {
    private var test = false

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
    }

    fun startExercise(view: View) {
        Toast.makeText(this@MainActivity, "Starting Exercise !", Toast.LENGTH_SHORT).show();
        startActivity(Intent(this@MainActivity, excerciseListActivity1::class.java))
    }

}
