package com.gymandroid.ui.exercise.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()
    private val moves = listOfNotNull<String>("Tree", "Cobra")
    private val details = listOfNotNull<String>(
        "Tree Pose stretches the thighs, groins, torso, and shoulders. It builds strength in the ankles and calves",
        "Cobra Pose is best known for its ability to increase the flexibility of the spine. "
    )
    private val cautions = listOfNotNull<String>(
        "Due to the balancing nature of the posture, do not practice Tree Pose if you are currently experiencing headaches, insomnia, low blood pressure, or if you are lightheaded and/or dizzy. Those with high blood pressure should not raise their arms overhead in the pose. ",
        "Please do not practice Cobra if you have carpal tunnel syndrome, or a recent back or wrist injury. Women who are pregnant should avoid practicing this pose while on the floor, although they may practice it standing with their palms against a wall. "
    )
    private val uries = listOfNotNull<Int>( 2131755012, 2131755008)


    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 2

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i, moves[i-1].toString()))
        }

    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int, name: String): DummyItem {
        return DummyItem(position.toString(), name, makeDetails(position), uries[position - 1], cautions[position - 1])
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append(details[position-1])
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val details: String, val uri: Int, val caution: String) {
        override fun toString(): String = content
    }
}
