package com.gymandroid.Settings

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.gymandroid.R
import kotlinx.android.synthetic.main.activity_setting_account.*

class SettingsAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState)
        var page = intent.getIntExtra("Page", 0)
        if (page == 0) {
            setContentView(R.layout.activity_setting_account)
        } else if (page == 1) {
            setContentView(R.layout.activity_setting_notification)
        } else if (page == 2) {
            setContentView(R.layout.activity_setting_privacy)
        } else if (page == 3) {
            setContentView(R.layout.activity_setting_general)
            val bmi: Button = findViewById(R.id.calculate_bmi) as Button
            bmi.setOnClickListener {
                val height: EditText = findViewById(R.id.height) as EditText
                val heightvalue: Double = height.text.toString().toDouble()
                val weight: EditText = findViewById(R.id.weight) as EditText
                val weightvalue: Double = weight.text.toString().toDouble()
                val bmivalue: Double = weightvalue / (heightvalue * heightvalue / 10000)
                val bmi_output: TextView = findViewById(R.id.bmi) as TextView
                bmi_output.text = "%.2f".format(bmivalue)
            }
        } else if (page == 4) {
            setContentView(R.layout.activity_setting_contactus)
        } else if (page == 5) {
            setContentView(R.layout.activity_setting_aboutus)
        }
        account_bar.setOnClickListener {
            finish()
        }
    }
}