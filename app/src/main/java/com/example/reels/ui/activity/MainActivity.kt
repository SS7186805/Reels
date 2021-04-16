package com.example.reels.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.example.reels.R
import com.example.reels.utils.SharedPrefernces

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        /** Making this activity, full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable{
            if (SharedPrefernces.init().is_Login){
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,AuthActivity::class.java))
                finish()
            }
        },3000)
    }
}