package com.gymandroid.ui.exercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.VideoView
import com.gymandroid.R
import com.gymandroid.ui.exercise.dummy.DummyContent.ITEMS

/**
 * An activity representing a single excercise detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [excerciseListActivity].
 */
class ExcerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise)

    }
}
