package com.example.passwordmanager.screens.page.Home_screen

import AddPasswordDialog
import PasswordAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.Database.PasswordEntryDbHelper
import com.example.passwordmanager.Models.PasswordEntry
import com.example.passwordmanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
class Home_screen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var passwordAdapter: PasswordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val passwordList = listOf(
            PasswordEntry(1L, "Gmail", "a123@gmail.com","password1"),
            PasswordEntry(2L, "Facebook", "abc89@gmail.com","password2"),
            PasswordEntry(3L, "Twitter", "ab25@gmail.com","password3"),
            PasswordEntry(4L, "Instagram", "ab23@gmail.com","password4"),
            PasswordEntry(5L, "Snapchat", "ab14@gmail.com","password5")
        )

        // Initialize dbHelper with the context of the current activity
        val dbHelper = PasswordEntryDbHelper(this)

        // Use dbHelper to get password entries
        val passwordEntry = dbHelper.getPasswordEntries()

        recyclerView = findViewById(R.id.Password_List)
        recyclerView.layoutManager = LinearLayoutManager(this)
        passwordAdapter = PasswordAdapter(passwordList)
        recyclerView.adapter = passwordAdapter


        val fabAddPassword = findViewById<FloatingActionButton>(R.id.add_password_btn)
        fabAddPassword.setOnClickListener {
            val addPasswordDialog = AddPasswordDialog(this)
            addPasswordDialog.showDialog()
        }
    }


}