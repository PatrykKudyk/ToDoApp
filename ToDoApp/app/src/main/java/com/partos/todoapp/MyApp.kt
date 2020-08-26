package com.partos.todoapp

import android.app.Application

class MyApp : Application() {

    companion object {
        var dateId = 0L
        var recyclerUpdate = false
//        var clearColors = false
    }
}