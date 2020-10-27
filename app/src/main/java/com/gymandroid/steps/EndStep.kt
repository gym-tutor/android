package com.gymandroid.steps

import com.gymandroid.Helper

class EndStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {
    override suspend fun action() {
        this.speak("This is the last step")
        finished = true
    }

    override fun isEndStep(): Boolean {
        return true
    }
}