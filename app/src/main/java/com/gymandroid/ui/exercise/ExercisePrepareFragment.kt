package com.gymandroid.ui.exercise

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gymandroid.R


class ExercisePrepareFragment : Fragment() {
    private var countdownNumber = 6;
    private lateinit var videoView: VideoView
    private lateinit var infoView: TextView
    private var isPlaying: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_exercise_prepare, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoView = view.findViewById(R.id.exercising_video)
        infoView = view.findViewById(R.id.exercising_info)

        videoView.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
            Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.tree)
        )

        // hide controls
        val ctrl = MediaController(requireContext())
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
            infoView.text = "Ready..."
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
