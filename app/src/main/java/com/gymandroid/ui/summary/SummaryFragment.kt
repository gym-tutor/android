package com.gymandroid.ui.summary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gymandroid.ExerciseRecord
import com.gymandroid.R
import com.gymandroid.SummaryDetailsActivity
import com.gymandroid.getRecords
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class Record(private val recordData: ExerciseRecord): Serializable {
    val type: String get() = recordData.type
    val analysis: String get() = recordData.analysis
    val correctRate: Int get() = recordData.correctRate.roundToInt()

    val subtitle: String
        get() {
            val past = Date(recordData.unixTimestamp * 1000)
            val now = Date()
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            when {
                seconds < 60 -> {
                    return "$seconds seconds ago"
                }
                minutes < 60 -> {
                    return "$minutes minutes ago"
                }
                hours < 24 -> {
                    return "$hours hours ago"
                }
                days < 7 -> {
                    return "$days days ago"
                }
                else -> {
                    return SimpleDateFormat("HH:mm E L M, Y", Locale.getDefault()).format(past)
                }
            }
        }

    val description: String
        get() {
            return "${"%.1f".format(recordData.hoursSpent)} hours, ${recordData.correctRate.roundToInt()}% correct rate"
        }
}

class RecordCardAdapter(val items: List<Record>, val gotoDetails: (Record) -> Unit) :
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
        holder.layout.setOnClickListener {
            gotoDetails(items[position])
        }
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

        val recordsData = getRecords(activity!!)
        val records = recordsData.map(::Record)
        val summaryRecordManager = LinearLayoutManager(activity)
        val summaryRecordAdapter = RecordCardAdapter(records, gotoDetailsPage)
        val summaryRecordList: RecyclerView = root.findViewById(R.id.summary_record_list)
        summaryRecordList.apply {
            layoutManager = summaryRecordManager
            adapter = summaryRecordAdapter
        }
        return root
    }

    private val gotoDetailsPage: (Record) -> Unit = {
        val intent = Intent(activity, SummaryDetailsActivity::class.java)
        intent.putExtra("record", it)
        startActivity(intent)
        activity?.overridePendingTransition(
            R.anim.enter_new_from_right,
            R.anim.enter_old_from_right
        )
    }
}
