package com.gymandroid.ui.exercise

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.gymandroid.R
import com.gymandroid.ui.exercise.dummy.YogaPoseRepository
import java.io.File

/**
 * A fragment representing a single excercise detail screen.
 * This fragment is either contained in a [excerciseListActivity]
 * in two-pane mode (on tablets) or a [ExcerciseActivity]
 * on handsets.
 */
class ExcerciseDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private lateinit var mViewModel: ExerciseViewModel;
    private lateinit var poseInfo: YogaPoseRepository.YogaPoseInfo;


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(requireActivity()).get(ExerciseViewModel::class.java)

        mViewModel.setYogaPoseInfo(poseInfo)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val poseInfo = YogaPoseRepository.YogaPoseInfo(
            requireActivity().intent.getStringExtra("name"),
            requireActivity().intent.getStringExtra("content"),
            requireActivity().intent.getStringExtra("detail"),
            requireActivity().intent.getStringExtra("descVideoFileName"),
            requireActivity().intent.getStringExtra("caution"),
            requireActivity().intent.getStringExtra("descImgFileName"),
            requireActivity().intent.getStringArrayListExtra("yogaStepVideoNames")

        )
        this.poseInfo = poseInfo

        val rootView = inflater.inflate(R.layout.fragment_excercise_detail, container, false)

        val videoView = rootView.findViewById<VideoView>(R.id.videoView)
        Log.w("hello", poseInfo.descVideoFileName)
        val video_path = File(
            requireActivity().filesDir,poseInfo.descVideoFileName)
        Log.w("hello", video_path.absolutePath)



        videoView.setVideoURI(
            video_path.toUri()

        )
        videoView.start()


        val yoga_caution_text = rootView.findViewById<TextView>(R.id.yoga_caution_text)
        val yoga_detail_text = rootView.findViewById<TextView>(R.id.yoga_detail_text)
        val yoga_content_text = rootView.findViewById<TextView>(R.id.yoga_content_text)

        yoga_caution_text.text = poseInfo.caution
        yoga_detail_text.text = poseInfo.detail
        yoga_content_text.text = poseInfo.content

        Log.w("onCreateView",yoga_detail_text.text.toString() )
//        if (savedInstanceState == null) {
//            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//            val fragment = ExcerciseDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(
//                        ExcerciseDetailFragment.ARG_ITEM_ID,
//                        intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID)
//                    )
//                }
//            }
//
////            requireActivity().supportFragmentManager.beginTransaction()
////                .add(R.id.excercise_detail_container, fragment)
////                .commit()
//        }



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            Navigation.findNavController(
                requireActivity(),
                R.id.exercise_nav_host_fragment
            ).navigate(R.id.action_excerciseDetailFragment_to_exerciseGyroFragment)

        }

    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
