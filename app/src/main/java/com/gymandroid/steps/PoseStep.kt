package com.gymandroid.steps

import com.gymandroid.Helper

class PoseStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {
    var instruction = ""
        set(message) {
            field = message
        }


    override suspend fun action() {
        var message = this.instruction
        this.speak(message)
    }

    override fun isPoseStep(): Boolean {
        return true
    }
}