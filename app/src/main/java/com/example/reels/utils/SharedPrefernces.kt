package com.example.reels.utils

import android.content.Context.MODE_PRIVATE
import com.example.reels.AppController

class SharedPrefernces {
   private val sharedPrefernces = AppController.init().applicationContext.getSharedPreferences("AppInfo",MODE_PRIVATE)
   private val editor = sharedPrefernces.edit()

    init {
        instance = this
    }
    companion object{
        private var instance :SharedPrefernces ?= null
        fun init():SharedPrefernces{
            if (instance==null){
                instance = SharedPrefernces()
            }
            return instance!!
        }
    }



    //for set sharedPrefernces
   //________________________________________________________________________________________________________________________________________//

    private val isLogin = "loginStatus"

    var is_Login:Boolean
    get() {
        return sharedPrefernces.getBoolean(isLogin,false)
    }
    set(value) {
        editor.putBoolean(isLogin,value).apply()
    }


    fun clearData(){
        editor.clear().commit()
    }

    fun storeData(key:String,value:String){

    }

}