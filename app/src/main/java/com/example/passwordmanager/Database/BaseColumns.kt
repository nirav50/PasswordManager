package com.example.passwordmanager.Database

import android.provider.BaseColumns

object PasswordEntryContract {
    // Table contents
    class PasswordEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "password_entries"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_NAME_PASSWORD = "password"
        }
    }
}
