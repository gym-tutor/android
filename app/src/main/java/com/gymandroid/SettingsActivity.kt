package com.gymandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        settings_bar.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.exit_old_from_right, R.anim.exit_new_from_right)
        }
        val bmi: Button = findViewById(R.id.calculate_bmi) as Button
        bmi.setOnClickListener{
            val height: EditText = findViewById(R.id.height) as EditText
            val heightvalue:Double = height.text.toString().toDouble()
            val weight: EditText = findViewById(R.id.weight) as EditText
            val weightvalue:Double = weight.text.toString().toDouble()
            val bmivalue: Double = weightvalue/(heightvalue*heightvalue/10000)
            val bmi_output: TextView = findViewById(R.id.bmi) as TextView
            bmi_output.text ="%.2f".format(bmivalue)
        }
    }
}
