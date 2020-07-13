package com.gymandroid.steps

import android.util.Log
import com.gymandroid.Helper
import com.google.gson.JsonObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class BreathStep(pose:String,id:Int,helper: Helper): Step(pose,id,helper){
    var breath_times = 0
        set(value){
            field = value
        }
    private fun sendInfoToBackend(img:String ) : JSONObject {

        val jsonObj = JsonObject()
        jsonObj.addProperty("pose",pose)
        jsonObj.addProperty("id", curr_id)
        jsonObj.addProperty("command","detect")
        jsonObj.addProperty("image",img)
        Log.w("In sendInfoToBackend","prepare json " +jsonObj.toString())
        helper.server.getEvaluateMessage(jsonObj)
        while(helper.server.result == null)continue
        Log.w("In sendInfoToBackend",helper.server.result.toString())
        val result = JSONObject(helper.server.result.toString())
        helper.server.result = null
        return result

    }

    private suspend fun breath(){
        speak("breath " + breath_times.toString() + " times")
        for(i in 1 .. breath_times){
            makeBreathVoice()
        }

    }

    override suspend fun action() {

        delay(2000)

        var result: JSONObject? = null
        var a = launch{breath ()}
        Log.e("In Photo Step","thread 2 ")
        result = sendInfoToBackend(takePhoto()!!)
        Log.e("In Photo Step","thread 2 image send success ")
        if (result?.get("pass") != true ){
            a.cancel()
            helper.speaker.mTTS.setSpeechRate(1f)
            this.speak(result?.get("message") as String)
            this.speak("Please try again")
            this.repeat = true
        }else{
            a.join()
            this.speak(result?.get("message") as String)
        }


        //this.speak("finish")

    }

}