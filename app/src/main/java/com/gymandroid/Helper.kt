package com.gymandroid

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.yogacomponentdemo.ServerHelper.ServerHelper
import com.example.yogacomponentdemo.SpeakerHelper.SpeakerHelper
import com.example.yogacomponentdemo.CameraHelper.CameraHelper

class Helper(val context: Context) {
    var camera = CameraHelper(context)
        private set
    var speaker = SpeakerHelper(context)
        private set

    var server = ServerHelper()
        private set

    fun start(lifecycleOwner: LifecycleOwner) {
        camera.startCamera(lifecycleOwner)
    }

    companion object {
        private var instance: Helper? = null;
        fun getInstance(context: Context): Helper {
            if (instance == null)
                instance = Helper(context)

            return instance!!
        }

        fun createNew(context: Context):Helper{
            instance = Helper(context)
            return instance!!;
        }
    }


}