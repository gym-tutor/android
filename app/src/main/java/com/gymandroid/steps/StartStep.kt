package com.gymandroid.steps

import com.gymandroid.Helper
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject

class StartStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {

    override suspend fun action() {
        val jsonObj = JsonObject()
        jsonObj.addProperty("yoga_name", pose)

        helper.server.getStepMessages(jsonObj)
        while (helper.server.result == null) continue
        var steps_list = helper.server.result!!.get("steps") as JSONArray
        var id = 0
        var step_buffer: Step = this
        for (i in 0 until steps_list.length()) {

            var step_info = steps_list.get(i) as JSONObject
            when (step_info.get("category") as String) {
                "pose" -> {
                    id++
                    val pose_step = PoseStep(pose, id, helper)
                    pose_step.instruction = step_info.get("message") as String
                    step_buffer.setNextStep(pose_step)
                }
                "breath" -> {
                    val breath_step = BreathStep(pose, id, helper)
                    breath_step.breath_times = step_info.get("times") as Int
                    step_buffer.setNextStep(breath_step)
                }

            }
            step_buffer = step_buffer.next()!!
        }
        step_buffer.setNextStep(RelaxStep(pose, id, helper))
        step_buffer = step_buffer.next()!!
        step_buffer.setNextStep(EndStep(pose, id, helper))
        helper.server.result = null
        this.speak("Let's Exercise")
        delay(1000)
    }

    override fun isStartStep(): Boolean {
        return true
    }
}