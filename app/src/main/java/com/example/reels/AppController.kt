package com.example.reels

import android.app.Application

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
       instance = this
    }

    companion object{
         var instance:AppController ?= null
        fun init():AppController{
            if (instance==null){
                instance = AppController()
            }
            return instance!!
        }
    }
}