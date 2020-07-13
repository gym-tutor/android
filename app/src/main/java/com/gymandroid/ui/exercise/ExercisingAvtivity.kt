package com.gymandroid.ui.exercise

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.gymandroid.R


class ExercisingActivity : AppCompatActivity() {
    private var countdownNumber = 6;
    private lateinit var videoView: VideoView
    private lateinit var infoView: TextView
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercising)

        videoView = findViewById(R.id.exercising_video)
        infoView = findViewById(R.id.exercising_info)

        videoView.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tree)
        )

        // hide controls
        val ctrl = MediaController(this)
        ctrl.visibility = View.GONE
        ctrl.setMediaPlayer(videoView)
        videoView.setMediaController(ctrl)
        ctrl.hide()

        // hide video and show later
        videoView.alpha = 0f

        // start count down
        blink()
    }

    fun blink() {
        if (!isPlaying) {
            isPlaying = true
            Handler().postDelayed({
                val animation: Animation = AlphaAnimation(0f, 1f)
                animation.duration = 300
                videoView.startAnimation(animation)
                videoView.alpha = 1f
                videoView.start()
            }, 0)
        }
        --countdownNumber
        if (countdownNumber < 0) {
            infoView.text = "Exercising"
        } else if (countdownNumber == 0) {
            infoView.text= "Ready..."
        } else {
            val infoText = countdownNumber.toString()
            infoView.text = infoText
        }
        val anim = blinkAnim()
        infoView.startAnimation(anim)
        Handler().postDelayed({
            blink()
        }, 1000)
    }

    fun blinkAnim(): Animation? {
        val animation: Animation = AlphaAnimation(1f, 0.5f)
        animation.duration = 1000
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = 1
        animation.repeatMode = Animation.REVERSE
        return animation
    }
}
