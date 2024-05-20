package com.example.passwordmanager.screens.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passwordmanager.R
import com.example.passwordmanager.screens.page.lock_screen.Lock_screen

class Splesh_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh_screen)

        // Simulate splash screen delay
        Thread.sleep(2000)

        // Start lock activity after splash screen
        startActivity(Intent(this,Lock_screen::class.java))
        finish()
    }
}