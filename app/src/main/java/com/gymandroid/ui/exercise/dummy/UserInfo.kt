package com.gymandroid.ui.exercise.dummy

object UserInfo {
    private var height:Double=0.0
    private var weight:Double=0.0

    fun set_height(h: Double) {
        height = h
    }

    fun set_weight(w: Double) {
        weight = w
    }

    fun get_height(): Double{
        return height
    }

    fun get_weight(): Double {
        return weight
    }
}