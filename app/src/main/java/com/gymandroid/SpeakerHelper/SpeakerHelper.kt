package com.example.yogacomponentdemo.SpeakerHelper

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import com.gymandroid.R
import java.util.*


class SpeakerHelper(context: Context) {
    val context = context
    private val RECORD_REQUEST_CODE=101
    lateinit var mTTS: TextToSpeech
    var ready = false

    init{
        mTTS= TextToSpeech(context,TextToSpeech.OnInitListener{
                status -> if(status!=TextToSpeech.ERROR){
            mTTS.language= Locale.UK
            mTTS.setPitch(1.3f)
            ready = true
        }
        })
    }
    fun speak(message:String,waitSpeak:Boolean = true){
        mTTS.speak(message,TextToSpeech.QUEUE_FLUSH,null,null)
        if (waitSpeak) {
            while (mTTS.isSpeaking()) {
                continue
            }
        }
    }
    fun breath(){
        Log.w("breath","start breath")
        mTTS.addEarcon("[breath]",context.packageName, R.raw.breath)
        var result = mTTS.playEarcon("[breath]", TextToSpeech.QUEUE_ADD,null)
        Log.w("breath",result.toString())
    }
    fun stop(){
        if (mTTS.isSpeaking()){
            mTTS.stop()
        }
    }
    fun checkPermission(){

    }

}