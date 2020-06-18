package com.gymandroid

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import com.gymandroid.ui.summary.Record
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonConfiguration

private val json = Json(JsonConfiguration.Stable.copy())
private val defaultRecordJSON = "{\"records\": []}"

@Serializable
data class ExerciseRecord(
    val unixTimestamp: Long,
    val type: String,
    val correctRate: Float,
    val hoursSpent: Float,
    val analysis: String
)

@Serializable
private data class ExerciseRecords(val records: ArrayList<ExerciseRecord>) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return records.hashCode()
    }
}

fun addRecords(activity: ComponentActivity, record: ExerciseRecord): Boolean {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val currentRecordsEncoded =
        sharedPref.getString("records", defaultRecordJSON) ?: defaultRecordJSON
    val records: ExerciseRecords = Json.parse(ExerciseRecords.serializer(), currentRecordsEncoded)
    records.records.add(record)

    val modifiedRecordsEncoded = json.stringify(ExerciseRecords.serializer(), records)
    with(sharedPref.edit()) {
        putString("records", modifiedRecordsEncoded)
        commit()
    }
    return true
}


fun getRecords(activity: ComponentActivity): ArrayList<ExerciseRecord> {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val currentRecordsEncoded =
        sharedPref.getString("records", defaultRecordJSON) ?: defaultRecordJSON
    val records: ExerciseRecords = Json.parse(ExerciseRecords.serializer(), currentRecordsEncoded)
    return records.records
}

fun clearRecords(activity: ComponentActivity) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("records", defaultRecordJSON)
        commit()
    }
}