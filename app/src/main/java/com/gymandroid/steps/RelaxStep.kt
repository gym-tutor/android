package com.gymandroid.steps

import com.gymandroid.Helper
import kotlinx.coroutines.delay

class RelaxStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {
    override suspend fun action() {

        delay(1000)
        this.speak("It's finished. Relax your body")
        finished = true
    }

    override fun isRelaxStep(): Boolean {
        return true
    }

}