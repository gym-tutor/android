package com.gymandroid.ui.exercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.gymandroid.R
import com.gymandroid.YogaActivity
import com.gymandroid.ui.exercise.dummy.DummyContent
import com.gymandroid.ui.exercise.dummy.DummyContent.ITEMS
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
    var pose_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excercise_detail)
//        Log.d("videohahaha", intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID))
//        Log.d("videohahaha", R.raw.tree.toString())
//        Log.d("videohahaha", ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].content)


        videoView2 = findViewById(R.id.videoView)

        videoView2.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
//            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cobra_2)
            Uri.parse("android.resource://" + getPackageName() + "/"+ ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].uri)

        )
        videoView2.start()
        pose_id = intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt()-1
        textView.text = ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].content
        detail?.text = ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].details
        caution?.text = ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].caution
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

    override fun onResume() {
        super.onResume()
        videoView2 = findViewById(R.id.videoView)

        videoView2.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
//            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cobra_2)
            Uri.parse("android.resource://" + getPackageName() + "/"+ ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].uri)

        )
        videoView2.start()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, excerciseListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    fun start_gyro(view: View) {
//        startActivity(Intent(this, gyroscope::class.java).apply{
//            putExtra(excerciseDetailFragment.ARG_ITEM_ID,intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1)
//        })


        val intent = Intent(this, YogaActivity::class.java)
        intent.putExtra("POSE_ID",pose_id)
        startActivity(intent)
    }
}
