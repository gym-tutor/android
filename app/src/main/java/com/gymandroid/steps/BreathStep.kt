package com.gymandroid.steps

import android.util.Log
import com.example.yogacomponentdemo.ServerHelper.ServerHelper
import com.google.gson.JsonObject
import com.gymandroid.Helper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable

class BreathStep(pose: String, id: Int, helper: Helper) : Step(pose, id, helper) {
    private lateinit var breathLaunch: Job;


    var breath_times = 0
        set(value) {
            field = value
        }

    private suspend fun breath() {

        for (i in 1..breath_times) {
            makeBreathVoice()
        }

    }

    override suspend fun action() {

        delay(2000)

        this.speak("breath " + breath_times.toString() + " times")

        breathLaunch = launch { breath() }

        Log.e("In Photo Step", "thread 2 ")
        val img = takePhoto()!!

        val jsonObj = JsonObject()
        jsonObj.addProperty("pose", pose)
        jsonObj.addProperty("id", curr_id)
        jsonObj.addProperty("command", "detect")
        jsonObj.addProperty("image", img)
        Log.w("In sendInfoToBackend", "prepare json " + jsonObj.toString())
        helper.server.getEvaluateMessage(jsonObj,object: ServerHelper.ReceiveMessageCallback{
            override fun onReceive(result: JSONObject) {
                Log.e("In Photo Step", "thread 2 image send success ")
                if (result?.get("pass") != true) {
                    breathLaunch.cancel()
                    helper.speaker.mTTS.setSpeechRate(1f)
                    this@BreathStep.speak(result?.get("message") as String)
                    this@BreathStep.speak("Please try again")
                    this@BreathStep.repeat = true
                    finished = true
                } else {
                    suspend {
                        breathLaunch.join()
                        this@BreathStep.speak(result?.get("message") as String )
                        finished = true
                    }
                }
            }

            override fun onFailure() {
                breathLaunch.cancel()
                this@BreathStep.speak("Network fail")
            }

        })



        //this.speak("finish")

    }

}