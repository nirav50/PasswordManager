package com.example.passwordmanager.screens.page.lock_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.passwordmanager.R
import com.example.passwordmanager.screens.page.Home_screen.Home_screen
import java.util.concurrent.Executor

class Lock_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var executor: Executor
        lateinit var biometricPrompt: BiometricPrompt
        lateinit var promptInfo: BiometricPrompt.PromptInfo

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)


            executor = ContextCompat.getMainExecutor(this)


            biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(applicationContext,
                            "Authentication succeeded!", Toast.LENGTH_SHORT)
                            .show()
                        // Redirect to the second activity
                        startActivity(Intent(applicationContext,Home_screen::class.java))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build()

            // Prompt appears when user clicks "Log in".
            // Consider integrating with the keystore to unlock cryptographic operations,
            // if needed by your app.
            val biometricLoginButton =
                findViewById<Button>(R.id.biometric_login)
            biometricLoginButton.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }


    }
}