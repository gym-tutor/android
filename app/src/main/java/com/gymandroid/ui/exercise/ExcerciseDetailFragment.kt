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
import androidx.navigation.Navigation
import com.gymandroid.R
import com.gymandroid.ui.exercise.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_excercise_detail.*

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
    private var item: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
//                activity?.toolbar_layout?.title = item?.content
            }
        }

        //        Log.d("videohahaha", intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID))
//        Log.d("videohahaha", R.raw.tree.toString())
//        Log.d("videohahaha", ITEMS[intent.getStringExtra(excerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].content)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_excercise_detail, container, false)

        val videoView = rootView.findViewById<VideoView>(R.id.videoView)
        val intent =requireActivity().intent
        videoView.setVideoURI(
            // https://www.shutterstock.com/video/clip-16847116-fitness-yoga-animation
//            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cobra_2)
            Uri.parse(
                "android.resource://" + requireActivity().getPackageName() + "/"
                        + DummyContent.ITEMS[intent.getStringExtra(
                    ExcerciseDetailFragment.ARG_ITEM_ID
                ).toInt() - 1].uri
            )
        )
        videoView.start()

        val pose_id = requireActivity().intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID).toInt() - 1


        val yoga_caution_text = rootView.findViewById<TextView>(R.id.yoga_caution_text)
        val yoga_detail_text = rootView.findViewById<TextView>(R.id.yoga_detail_text)
        val yoga_content_text = rootView.findViewById<TextView>(R.id.yoga_content_text)

        yoga_content_text.text =
            DummyContent.ITEMS[intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].content
        yoga_detail_text?.text =
            DummyContent.ITEMS[intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].details
        yoga_caution_text?.text =
            DummyContent.ITEMS[intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID).toInt() - 1].caution
//
//
        Log.w("onCreateView",yoga_detail_text.text.toString() )
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = ExcerciseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        ExcerciseDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(ExcerciseDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

//            requireActivity().supportFragmentManager.beginTransaction()
//                .add(R.id.excercise_detail_container, fragment)
//                .commit()
        }



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
