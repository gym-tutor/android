package com.gymandroid.ui.exercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.gymandroid.R
import kotlinx.android.synthetic.main.activity_excercise_detail.*
import kotlinx.android.synthetic.main.activity_excercise_detail.videoView as videoView1

/**
 * An activity representing a single excercise detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [excerciseListActivity].
 */
class excerciseDetailActivity : AppCompatActivity() {

    private lateinit var videoView2: VideoView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_detail)
//        setSupportActionBar(detail_toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        // Show the Up button in the action bar.
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //



        videoView2 = findViewById(R.id.videoView)

        videoView2.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
//            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cobra_2)
            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cobra_2)

        )
        videoView2.start()
//        val ctrl = MediaController(this)
//        ctrl.visibility = View.GONE
//        ctrl.setMediaPlayer(videoView)
//        videoView.setMediaController(ctrl)
//        ctrl.hide()


        //intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID)
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = excerciseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        excerciseDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.excercise_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, excerciseListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
