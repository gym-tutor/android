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
import com.gymandroid.ui.exercise.dummy.DummyContent
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise.*


class ExerciseFragment : Fragment() {

    private lateinit var viewmodel: ExerciseViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewmodel =
            ViewModelProviders.of(this).get(viewmodel::class.java)
        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
        val textView: TextView = root.findViewById(R.id.is_pos)
        viewmodel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
//        is_pos.run {
//            is_pos.text = "hello"
//        }

        return root
    }

}
