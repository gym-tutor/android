package com.gymandroid.ui.exercise.dummy

import android.util.JsonReader
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Array.newInstance
import javax.xml.datatype.DatatypeFactory.newInstance

class Recommendations {

    private var height:Double=UserInfo.get_height()
    private var weight:Double=UserInfo.get_weight()

//    private var height:Double=0.0
//    private var weight:Double=0.0


    fun set_height(h: Double) {
        height = h
    }

    fun set_weight(w: Double) {
        weight = w
    }


    fun loadJson(inputStream: InputStream) {

        val reader = JsonReader(InputStreamReader(inputStream));
        readMessage(reader)
        reader.close()
    }


    @Throws(IOException::class)
    private fun readMessage(reader: JsonReader) {
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()


            when(name){
                "height" -> height = reader.nextDouble()
                "weight" -> weight = reader.nextDouble()
                else -> reader.skipValue()
            }

        }
        reader.endObject()
    }

    override fun toString(): String {
        val base:String = "Based on your personal health data\nThis Yoga Pose may be a better fit for you\n"
        if (weight/height < 80/180) {
            return base + "Cobra"
        } else {
            return base + "Tree"
        }
    }


}