package com.gymandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gymandroid.ui.exercise.excerciseDetailFragment
import kotlinx.android.synthetic.main.activity_yoga.*
import kotlinx.coroutines.Job

class YogaActivity: AppCompatActivity() {
    var job: Job? = null
    lateinit var helper: Helper
    lateinit var yogaPose: YogaPose
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        helper = Helper.getInstance(this)
        helper.start(this)
        helper.speaker.onMessageChange = {
            runOnUiThread {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        }

        val pose_id = intent.getIntExtra("POSE_ID",0)
        val pose_name = if(pose_id ==0) "tree" else "cobra"
        yogaPose = YogaPose(pose_name, helper)

//        pause_btn.setOnClickListener {
//            yogaPose.pause()
//        }
//        continue_btn.setOnClickListener{
//            yogaPose.resume()
//        }
        yogaPose.onEndState = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        yogaPose.start()
        val videolist = getVideoList(pose_name)

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

    private fun getVideoList(pose_name:String):ArrayList<Int>{
        var videolist = ArrayList<Int>()
        when(pose_name){
            "tree" -> {
                videolist.add(R.raw.tree_1)
                videolist.add(R.raw.tree_2)
                videolist.add(R.raw.tree_3)
                videolist.add(R.raw.tree_4)
            }

            "cobra" -> {
                videolist.add(R.raw.cobra_1)
                videolist.add(R.raw.cobra_2)
                videolist.add(R.raw.cobra_3)
            }

        }
        return videolist

    }
    override fun onStart() {

        super.onStart()

    }
}