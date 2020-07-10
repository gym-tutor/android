package com.gymandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yoga.*
import kotlinx.coroutines.*

class YogaActivity: AppCompatActivity() {
    var job: Job? = null
    lateinit var helper: Helper
    lateinit var yogaPose: YogaPose
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        helper = Helper.getInstance(this)
        helper.start(this)
        yogaPose = YogaPose("tree", helper)
        pause_btn.setOnClickListener {
            yogaPose.pause()
        }
        continue_btn.setOnClickListener{
            yogaPose.resume()
        }
        yogaPose.onEndState = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        yogaPose.start()
        var videolist = ArrayList<Int>()
        videolist.add(R.raw.tree_1)
        videolist.add(R.raw.tree_2)
        videolist.add(R.raw.tree_3)
        videolist.add(R.raw.tree_4)

        videoView.setOnPreparedListener {
            videoView.start()
            yogaPose.start()
        }
        yogaPose.onStateChange = {

            runOnUiThread {
                if (it.isPoseStep()) {

                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videolist[it.curr_id - 1]))
                } else {
                    yogaPose.start()
                }
            }


        }


    }

    override fun onStart() {

        super.onStart()

    }
}