package com.example.ui_climaapp.SplashScreem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.ui_climaapp.R
import com.example.ui_climaapp.view.MainActivity

class SplashScreem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screem)

        //Moviendonos a otroa activity
        val splasScreemTimeOut = 4000
        val homeIntent = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splasScreemTimeOut.toLong())
    }
}