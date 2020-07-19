package com.gymandroid.steps

import com.gymandroid.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

abstract class Step(pose: String, id: Int, helper: Helper) : CoroutineScope {
    protected var pose = pose
    var curr_id = id
        protected set
    protected var next_step: Step? = null
    var repeat = false

    protected var helper = helper
    protected var max_step = 0

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main


    fun next(): Step? {
        return this.next_step
    }

    open fun isStartStep(): Boolean {
        return false
    }

    open fun isBreathStep(): Boolean {
        return false
    }

    open fun isPoseStep(): Boolean {
        return false
    }

    open fun isEvalStep(): Boolean {
        return false
    }

    open fun isEndStep(): Boolean {
        return false
    }

    open fun isRelaxStep(): Boolean {
        return false
    }

    fun takePhoto(): String {
        var camera = helper.camera
        camera.takePhoto()
        while (camera.getImage() == null) continue
        return camera.getImage()!!
    }

    //Current step repeating
    fun setNextStep(step: Step) {
        this.next_step = step
    }

    abstract suspend fun action();

    protected fun speak(message: String) {
        var speaker = helper.speaker
        speaker.speak(message)
    }

    protected fun makeBreathVoice() {
        var speaker = helper.speaker
        speaker.breath()
    }
}