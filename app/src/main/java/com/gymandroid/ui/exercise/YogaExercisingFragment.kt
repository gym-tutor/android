package com.gymandroid.ui.exercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.gymandroid.Helper
import com.gymandroid.MainActivity
import com.gymandroid.R
import com.gymandroid.YogaPose
import com.gymandroid.ui.exercise.dummy.YogaPoseRepository
import kotlinx.android.synthetic.main.fragment_yoga.*
import kotlinx.coroutines.Job
import java.io.File
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class YogaExercisingFragment : Fragment() {
    var job: Job? = null
    lateinit var helper: Helper
    private val TAG = "YogaExercisingFragment"
    lateinit var yogaPose: YogaPose
    private lateinit var mViewModel: ExerciseViewModel;
    private lateinit var poseInfo: YogaPoseRepository.YogaPoseInfo
    private lateinit var videolist:ArrayList<Uri>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = Helper.getInstance(requireContext())
        helper.start(this)
        helper.speaker.onMessageChange = {
            requireActivity().runOnUiThread() {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        mViewModel.getYogaPoseInfo().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            poseInfo = it
            videolist = getVideoList()
            yogaPose = YogaPose(poseInfo.name, helper)
            videoView.setVideoURI(videolist[0])
            videoView.setOnPreparedListener {
                videoView.seekTo( 1 );
            }

            startExerecse()

        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.w("Yoga Exercising","hi")
        mViewModel = ViewModelProviders.of(requireActivity()).get(ExerciseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_yoga, container, false)

        return root
    }

    fun startExerecse(){
        yogaPose.onEndState = {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        yogaPose.start()

        videoView.setOnPreparedListener {
            videoView.start()
            yogaPose.start()
        }
        yogaPose.onStateChange = {

            activity!!.runOnUiThread() {
                if (it.isPoseStep()) {

                    videoView.setVideoURI(videolist[it.curr_id - 1])
                } else {
                    yogaPose.start()
                }
            }


        }
    }


    private fun getVideoList(): ArrayList<Uri> {
        var videolist = ArrayList<Uri>()

        poseInfo.yogaStepVideoNames.forEach {
            videolist.add(File(requireActivity().filesDir,it).toUri()!!)

        }
        return videolist

    }

    override fun onDestroyView() {
        super.onDestroyView()
        helper.camera.closeCamera()
    }
}