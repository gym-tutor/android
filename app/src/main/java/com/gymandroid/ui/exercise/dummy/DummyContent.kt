package com.gymandroid.ui.exercise.dummy

import android.util.JsonReader
import kotlinx.serialization.UnstableDefault
import java.io.IOException
import java.io.Reader
import java.util.*
import kotlin.collections.ArrayList


@OptIn(UnstableDefault::class)
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    var ITEMS: MutableList<DummyItem> = ArrayList()
    private var count: Int = 0
    private val moves = mutableListOf<String>()
    private val details = mutableListOf<String>()
    private val cautions = mutableListOf<String>()
    private val uries = mutableListOf<Int>()
    private val imgs = mutableListOf<Int>()
    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    fun read(s: Reader) {
        if (count != 0)
            return
        val reader = JsonReader(s)
        reader.beginArray()
        while (reader.hasNext()) {
            readMessage(reader)
            count += 1
        }
        reader.endArray()
        reader.close()

    }

    fun build() {
        ITEMS.clear()
        for (i in 1..count) {
            addItem(createDummyItem(i, moves[i - 1].toString()))
        }
    }

    @Throws(IOException::class)
    fun readMessage(reader: JsonReader) {
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            if (name == "name") {
                moves.add(reader.nextString())
            } else if (name == "detail") {
                details.add(reader.nextString())
            } else if (name == "video_uri") {
                uries.add(reader.nextInt())
            } else if (name == "caution") {
                cautions.add(reader.nextString())
            } else if (name == "img_uri") {
                imgs.add(reader.nextInt())
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int, name: String): DummyItem {
        return DummyItem(
            position.toString(),
            name,
            details[position - 1],
            uries[position - 1],
            cautions[position - 1],
            imgs[position - 1]
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append(details[position - 1])
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(
        val id: String,
        val content: String,
        val details: String,
        val uri: Int,
        val caution: String,
        val img: Int
    ) {
        override fun toString(): String = content
    }
}
