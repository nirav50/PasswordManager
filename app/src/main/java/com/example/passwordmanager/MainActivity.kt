package com.example.passwordmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.passwordmanager.screens.page.Splesh_screen

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Simulate splash screen delay
        Thread.sleep(2000)

        // Start main activity after splash screen
        startActivity(Intent(this, Splesh_screen::class.java))
        finish()

    }
}