package com.gymandroid.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gymandroid.ui.exercise.dummy.YogaPoseRepository

class ExerciseViewModel : ViewModel() {
    private var yogaPoseInfo = MutableLiveData<YogaPoseRepository.YogaPoseInfo>()

    fun setYogaPoseInfo(item:YogaPoseRepository.YogaPoseInfo){
        yogaPoseInfo.value = item;
    }

    fun getYogaPoseInfo():MutableLiveData<YogaPoseRepository.YogaPoseInfo>{
        return yogaPoseInfo
    }
}