package com.gymandroid.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.gymandroid.R
import com.gymandroid.ui.exercise.dummy.DummyContent
import kotlinx.android.synthetic.main.excercise_list.*
import kotlinx.android.synthetic.main.excercise_list_content.view.*
import java.io.InputStream


class ExerciseSelectionFragment : Fragment() {

    private lateinit var viewmodel: ExerciseViewModel
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val `is`: InputStream = resources.openRawResource(R.raw.pose_info)
        DummyContent.read(`is`.bufferedReader())
        DummyContent.build()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_exercise, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (excercise_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        excercise_list.adapter = SimpleItemRecyclerViewAdapter(
                requireActivity(), DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: FragmentActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>()
    {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //            val idView: TextView = view.id_text
            val contentView: TextView = view.content
            val imgView: ImageView = view.findViewById(R.id.image)
        }

        private val onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.DummyItem
            if (twoPane) {
                val fragment = ExcerciseDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ExcerciseDetailFragment.ARG_ITEM_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.excercise_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ExcerciseActivity::class.java).apply {
                    putExtra(ExcerciseDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.excercise_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = values.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
//            holder.idView.text = item.id
            holder.contentView.text = item.content
            holder.imgView.setImageResource(item.img)

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }


    }
}
