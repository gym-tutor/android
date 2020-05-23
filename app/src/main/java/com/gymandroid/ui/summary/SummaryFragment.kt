package com.gymandroid.ui.summary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gymandroid.R
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.fragment_summary.view.*
import kotlinx.android.synthetic.main.fragment_summary.view.summary_record_list

class Record(val type: String, val subtitle: String, val description: String)

class RecordCardAdapter(val items: List<Record>) :
    RecyclerView.Adapter<RecordCardAdapter.RecordHolder>() {

    class RecordHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_summary_record_list, parent, false) as LinearLayout
        return RecordHolder(layout)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.summary_record_title).text = items[position].type
        holder.layout.findViewById<TextView>(R.id.summary_record_subtitle).text =
            items[position].subtitle
        holder.layout.findViewById<TextView>(R.id.summary_record_details).text =
            items[position].description
    }

}

class SummaryFragment : Fragment() {

    private lateinit var summaryViewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        summaryViewModel =
            ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_summary, container, false)

        val records = listOf(
            Record(
                "Report",
                "You exercised 4.8 hours last week",
                "2.3 hours were spent on sit-ups, 1.9 hours were spent on push-ups, and 0.6 hours were spent on jogging."
            ),
            Record("Sit-ups", "2 hours ago", "2.3 hours, 86% correct rate"),
            Record("Push-ups", "yesterday", "1.9 hours, 56% correct rate"),
            Record("Jogging", "3 days ago", "0.6 hours, 22% correct rate"),
            Record("Jogging", "3 days ago", "0.6 hours, 22% correct rate"),
            Record("Jogging", "3 days ago", "0.6 hours, 22% correct rate"),
            Record("Jogging", "3 days ago", "0.6 hours, 22% correct rate")
        )
        val summaryRecordManager = LinearLayoutManager(activity)
        val summaryRecordAdapter = RecordCardAdapter(records)
        val summaryRecordList: RecyclerView = root.findViewById(R.id.summary_record_list)
        summaryRecordList.apply {
            layoutManager = summaryRecordManager
            adapter = summaryRecordAdapter
        }
        return root
    }
}
