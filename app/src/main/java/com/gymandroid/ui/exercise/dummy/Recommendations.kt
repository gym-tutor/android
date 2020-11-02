package com.gymandroid.ui.exercise.dummy

import android.util.JsonReader
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class Recommendations {

    private var height:Double=0.0
    private var weight:Double=0.0

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
        return "Recommendations(height=$height, weight=$weight)"
    }


}