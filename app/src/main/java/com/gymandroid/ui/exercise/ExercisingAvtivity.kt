package com.gymandroid.ui.exercise

import com.gymandroid.R
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class ExercisingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercising)
        val videoView = findViewById<VideoView>(R.id.exercising_video)
        videoView.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.yoga_example)
        )
        videoView.setMediaController(MediaController(this))
        videoView.requestFocus()
        videoView.start()
    }
}
