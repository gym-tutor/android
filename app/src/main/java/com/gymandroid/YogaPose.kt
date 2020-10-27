package com.gymandroid

import android.util.Log
import com.example.yogacomponentdemo.ServerHelper.ServerHelper
import com.google.gson.JsonObject
import com.gymandroid.steps.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates.observable

class YogaPose(yogaName: String, helper: Helper) : CoroutineScope {
    var helper = helper
    var yogaName = yogaName
    private val TAG = "YogaPose"
    var start_step: Step = StartStep(yogaName, 0, helper)

    var curr_step: Step by observable(start_step) { _, oldValue, newValue ->
        onStateChange?.invoke(newValue)
    }

    init {
        Log.d(TAG, "init steps")
        val jsonObj = JsonObject()
        jsonObj.addProperty("yoga_name", yogaName)

        helper.server.getStepMessages(jsonObj, object : ServerHelper.ReceiveMessageCallback {
            override fun onReceive(result: JSONObject) {
                var step_buffer = start_step
                var steps_list = result!!.get("steps") as JSONArray
                var id = 0
                for (i in 0 until steps_list.length()) {

                    var step_info = steps_list.get(i) as JSONObject
                    when (step_info.get("category") as String) {
                        "pose" -> {
                            id++
                            val pose_step = PoseStep(yogaName, id, helper)
                            pose_step.instruction = step_info.get("message") as String
                            step_buffer.setNextStep(pose_step)
                        }
                        "breath" -> {
                            val breath_step = BreathStep(yogaName, id, helper)
                            breath_step.breath_times = step_info.get("times") as Int
                            step_buffer.setNextStep(breath_step)
                        }

                    }
                    step_buffer = step_buffer.next()!!
                }
                step_buffer.setNextStep(RelaxStep(yogaName, id, helper))
                step_buffer = step_buffer.next()!!
                step_buffer.setNextStep(EndStep(yogaName, id, helper))

            }

            override fun onFailure() {
            }

        })
    }
    var state: Int by observable(0) { _, oldValue, newValue ->
        if (newValue == 4) {
            onEndState?.invoke()
        }
    }
    var onStateChange: ((newStep: Step) -> Unit)? = null

    var onEndState: (() -> Unit)? = null

    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    fun start() {

        if (state == 0 || state == 2) {
            state = 1
            run()
        }
    }

    private fun run() {

        job = launch(Dispatchers.Unconfined) {

            delay(1000)
            if (state == 1) {
                if (!curr_step.isEndStep()) {
                    state = 0

                    curr_step.onFinished = {

                        if (curr_step.repeat) {
                            curr_step.finished=false
                            curr_step.repeat = false
                            curr_step = curr_step
                        } else {
                            curr_step = curr_step.next()!!
                        }
                    }
                    curr_step.action()

                } else {
                    state = 4

                }
            }
        }
    }

    fun pause() {
        if (state == 1) {
            state = 2
            job.cancel()
        }
    }

    fun resume() {
        start()

    }

    fun restart() {
        curr_step = start_step
    }



}
