package com.gymandroid.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gymandroid.R

class ExerciseFragment : Fragment() {

//    private lateinit var exerciseViewModel: ExerciseViewModel
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        exerciseViewModel =
//                ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        exerciseViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
        startActivity(Intent(activity, excerciseListActivity::class.java))
        return root
    }

}
