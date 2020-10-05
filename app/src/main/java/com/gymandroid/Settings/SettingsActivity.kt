package com.gymandroid.Settings

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.gymandroid.R
import kotlinx.android.synthetic.main.activity_setting.settings_bar


class SettingsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        settings_bar.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.exit_old_from_right,
                R.anim.exit_new_from_right
            )
        }

        val colortheme: Switch = findViewById<Switch>(R.id.change_theme)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            colortheme.isChecked = true;
        }
        colortheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }

        val account: LinearLayout = findViewById(R.id.account)
        account.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 0)
            startActivity(intent)
        }

        val notification: LinearLayout = findViewById(R.id.message_notifications)
        notification.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 1)
            startActivity(intent)
        }

        val priavacy: LinearLayout = findViewById(R.id.priavacy)
        priavacy.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 2)
            startActivity(intent)
        }

        val general: LinearLayout = findViewById(R.id.general)
        general.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 3)
            startActivity(intent)
        }

        val contactus: LinearLayout = findViewById(R.id.contact_us)
        contactus.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 4)
            startActivity(intent)
        }
        val aboutus: LinearLayout = findViewById(R.id.about)
        aboutus.setOnClickListener {
            val intent = Intent(this, SettingsAccount::class.java)
            intent.putExtra("Page", 5)
            startActivity(intent)
        }
    }
}
