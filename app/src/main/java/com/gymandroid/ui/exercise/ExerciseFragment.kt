package com.gymandroid.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.gymandroid.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise.*


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
//        val navHostFragment = nav_host_fragment as NavHostFragment
//        val params = navHostFragment.layoutInflater as ConstraintLayout.LayoutParams
//        params.verticalBias = 0.5f
        // val navHostFragment = navFragment as NavHostFragment
        // val host_view : NavHostFragment = findFragmentById(R.id.nav_host_fragment)
        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
//        button.setOnClickListener() {
//            startActivity(Intent(this.activity, excerciseListActivity::class.java))
//        }
        // startActivity(Intent(activity, excerciseListActivity::class.java))
        return root
    }

//    public fun excercise_start() {
//        startActivity(Intent(this.activity, excerciseListActivity::class.java))
//    }
//
//    button.
}
