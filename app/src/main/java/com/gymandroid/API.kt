/*
* Defines data structures and API interfaces used for exercise records
* */


package com.gymandroid

import android.content.Context
import androidx.activity.ComponentActivity
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonConfiguration

private val json = Json(JsonConfiguration.Stable.copy())
private val defaultRecordJSON = "{\"records\": []}"

/*
* The smallest unit of exercise record
* */
@Serializable
data class ExerciseRecord(
    var unixTimestamp: Long,  // timestamp in unix format
    val type: String,  // type of exercise (e.g. sit-up, push-up)
    var correctRate: Float,  // rate of correct exercise motion detected during the exercise
    var hoursSpent: Float,  // hours spent on this exercise
    var analysis: String  // detailed analysis (e.g. Most sit-ups are correct. Arm positions need to be corrected as your arms are ... most times. [More analysis...])
) : java.io.Serializable

@Serializable
private data class ExerciseRecords(val records: ArrayList<ExerciseRecord>) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return records.hashCode()
    }
}

/*
* Add an exercise record to local storage, sorted by time (most recent to least recent)
* Parameter activity: need to pass in the activity because of the implementation
* */
fun addRecords(activity: ComponentActivity, record: ExerciseRecord): Boolean {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val currentRecordsEncoded =
        sharedPref.getString("records", defaultRecordJSON) ?: defaultRecordJSON
    val records: ExerciseRecords = Json.parse(ExerciseRecords.serializer(), currentRecordsEncoded)
    records.records.add(record)
    records.records.sortByDescending { it.unixTimestamp }
    val modifiedRecordsEncoded = json.stringify(ExerciseRecords.serializer(), records)
    with(sharedPref.edit()) {
        putString("records", modifiedRecordsEncoded)
        commit()
    }
    return true
}

/*
* Return a list of exercise records stored locally
* */
fun getRecords(activity: ComponentActivity): ArrayList<ExerciseRecord> {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val currentRecordsEncoded =
        sharedPref.getString("records", defaultRecordJSON) ?: defaultRecordJSON
    val records: ExerciseRecords = Json.parse(ExerciseRecords.serializer(), currentRecordsEncoded)
    return records.records
}

/*
* If record exists, update the existing record
* Otherwise, add as a new record
*
* Record exists means there exists a record of the same type
* When performing record update, timestamp is updated to the same as the new record,
*   correct rate is the average of both,
*   hours spent are added together,
*   analysis is overwritten
* */
fun updateOrAddRecord(activity: ComponentActivity, record: ExerciseRecord): Boolean {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val currentRecordsEncoded =
        sharedPref.getString("records", defaultRecordJSON) ?: defaultRecordJSON
    val records: ExerciseRecords = Json.parse(ExerciseRecords.serializer(), currentRecordsEncoded)

    var updated = false
    for (recordExist in records.records) {
        if (record.type == recordExist.type) {
            updated = true
            recordExist.unixTimestamp = record.unixTimestamp
            recordExist.correctRate = (record.correctRate + recordExist.correctRate) / 2
            recordExist.hoursSpent += record.hoursSpent
            recordExist.analysis = record.analysis
        }
    }
    if (!updated) {
        records.records.add(record)
    }

    records.records.sortByDescending { it.unixTimestamp }
    val modifiedRecordsEncoded = json.stringify(ExerciseRecords.serializer(), records)
    with(sharedPref.edit()) {
        putString("records", modifiedRecordsEncoded)
        commit()
    }
    return true
}

/*
* Delete all locally stored exercise records
* */
fun clearRecords(activity: ComponentActivity) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("records", defaultRecordJSON)
        commit()
    }
}