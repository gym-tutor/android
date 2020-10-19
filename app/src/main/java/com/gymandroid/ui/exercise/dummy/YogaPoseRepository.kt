package com.gymandroid.ui.exercise.dummy

import android.content.Context
import android.util.JsonReader
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList


class YogaPoseRepository {

    /*
     * A map of sample (dummy) items, by ID.
     */

    private val yogaMap: MutableMap<String, YogaPoseInfo> = HashMap()

    fun loadJson(inputStream: InputStream) {

        val reader = JsonReader(InputStreamReader(inputStream));
        reader.beginArray()
        while (reader.hasNext()){
            readMessage(reader)
        }
        reader.endArray()
        reader.close()
    }


    @Throws(IOException::class)
    private fun readMessage(reader: JsonReader) {
        reader.beginObject()
        var poseInfo = YogaPoseInfo()
        while (reader.hasNext()) {
            val name = reader.nextName()


            when(name){
                "name" -> poseInfo.name = reader.nextString()
                "detail" -> poseInfo.detail = reader.nextString()
                "video_uri" -> poseInfo.descVideoFileName = reader.nextString()
                "caution" -> poseInfo.caution = reader.nextString()
                "img_uri" -> poseInfo.descImgFileName = reader.nextString()
                "video_segments" -> poseInfo.yogaStepVideoNames = readStringList(reader)
                else -> reader.skipValue()
            }

        }
        Log.w("readMessage","hello " + poseInfo.name + ' '+ poseInfo.descVideoFileName)
        yogaMap[poseInfo.name] = poseInfo
        reader.endObject()
    }

    private fun readStringList(reader: JsonReader):ArrayList<String>{

        var strList = ArrayList<String>()

        reader.beginArray()
        while (reader.hasNext()) {
            strList.add(reader.nextString())
        }
        reader.endArray()
        return strList
    }


    fun getYogaPoseInfoFromYogaName(yoga_id: String): YogaPoseInfo {
        return yogaMap[yoga_id] !!;
    }

    fun getAllYogaPoseInfoList(): List<YogaPoseInfo>{
        return ArrayList<YogaPoseInfo>(yogaMap.values);
    }


    /**
     * A dummy item representing a piece of content.
     */
    data class YogaPoseInfo(
        var name: String = "",
        var content: String = "",
        var detail: String = "",
        var descVideoFileName: String = "",
        var caution: String = "",
        var descImgFileName:String = "",
        var yogaStepVideoNames:ArrayList<String> = ArrayList<String>()
    ) {
        override fun toString(): String = content
    }
}
