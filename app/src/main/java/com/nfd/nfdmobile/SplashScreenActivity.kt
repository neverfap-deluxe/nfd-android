package com.nfd.nfdmobile

import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}
