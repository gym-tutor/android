package com.gymandroid

import com.gymandroid.steps.Step
import com.gymandroid.steps.StartStep
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates.observable

class YogaPose(yogaName: String, helper: Helper) : CoroutineScope {
    var helper = helper
    var yogaName = yogaName
    var start_step: Step = StartStep(yogaName, 0, helper)
    var curr_step: Step by observable(start_step) { _, oldValue, newValue ->
        onStateChange?.invoke(newValue)
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

    fun run() {

        job = launch(Dispatchers.Unconfined) {

            delay(1000)
            if (state == 1) {
                if (!curr_step.isEndStep()) {
                    state = 0
                    curr_step.action()
                    if (curr_step.repeat) {
                        curr_step.repeat = false
                        curr_step = curr_step
                    } else {
                        curr_step = curr_step.next()!!
                    }

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
