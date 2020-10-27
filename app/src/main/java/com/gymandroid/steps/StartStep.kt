package com.gymandroid.steps

import com.example.yogacomponentdemo.ServerHelper.ServerHelper
import com.gymandroid.Helper
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject

class StartStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {

    override suspend fun action() {
        this@StartStep.speak("Let's Exercise")
        delay(1000)
        finished = true

    }

    override fun isStartStep(): Boolean {
        return true
    }
}