package com.example.passwordmanager.Screens.home_screen

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.passwordmanager.Models.PasswordEntry
import com.example.passwordmanager.R
import com.example.passwordmanager.Screens.PasswordDialog.EditPasswordDialog

class Dialog_password_info(private val context: Context)  {

    // Inside the show function of Dialog_password_info class
    fun show(passwordEntry: PasswordEntry, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_dialog_password_info, null)
        val textName = dialogView.findViewById<TextView>(R.id.dialogNameTextView)
        val textUsername = dialogView.findViewById<TextView>(R.id.dialogUsernameTextView)
        val textPassword = dialogView.findViewById<TextView>(R.id.dialogPasswordTextView)
        val editButton = dialogView.findViewById<Button>(R.id.editButton)
        val deleteButton = dialogView.findViewById<Button>(R.id.deleteButton)
        val showHidePasswordButton = dialogView.findViewById<ImageButton>(R.id.showHidePasswordButton)

        var isPasswordVisible = false // Flag to track password visibility
        val maskedPassword = "*".repeat(passwordEntry.password.length)
        textPassword.text = maskedPassword // Initially, show masked password


        textName.text = passwordEntry.name
        textUsername.text = "${passwordEntry.username}"
        textPassword.text = "${passwordEntry.password}" // Mask the password initially

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        editButton.setOnClickListener {
            onEditClick()
            dialog.dismiss()
        }


        deleteButton.setOnClickListener {
            onDeleteClick()
            dialog.dismiss()
        }

        // Toggle password visibility when the image button is clicked
        showHidePasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible // Toggle password visibility

            if (isPasswordVisible) {
                textPassword.text = passwordEntry.password // Show password in plain text
                showHidePasswordButton.setImageResource(R.drawable.ic_password_visibility_on) // Change icon to hide password
            } else {
                textPassword.text = maskedPassword // Mask password again
                showHidePasswordButton.setImageResource(R.drawable.ic_password_visibility_off) // Change icon to show password
            }
        }

        dialog.show()
    }

}