package com.gymandroid.steps

import com.gymandroid.Helper
import kotlinx.coroutines.delay

class EvaluationStep(pose: String, id: Int, helper: Helper) :
    Step(pose, id, helper) {
    override suspend fun action() {
        this.speak("Evaluation step begins.")
        delay(1000)
        this.speak("Evaluation done.")
        this.speak("You did a good job.")
        if (this.repeat)
            this.setNextStep(PoseStep(pose, this.curr_id, helper))
        else {
            if (curr_id == 4) {
                this.setNextStep(EndStep(pose, this.curr_id, helper))
            } else {
                this.setNextStep(PoseStep(pose, this.curr_id + 1, helper))
            }
        }
        finished = true
    }
}