package com.example.passwordmanager.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.passwordmanager.Database.PasswordEntryContract
import com.example.passwordmanager.Models.PasswordEntry

class PasswordEntryDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${PasswordEntryContract.PasswordEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${PasswordEntryContract.PasswordEntry.COLUMN_NAME_NAME} TEXT," +
                    "${PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME} TEXT," +
                    "${PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD} TEXT)"

        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
    }

    fun insertPasswordEntry(name: String, username: String, password: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_NAME, name)
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME, username)
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD, password)
        }
        db.insert(PasswordEntryContract.PasswordEntry.TABLE_NAME, null, values)
    }

    fun getPasswordEntries(): List<PasswordEntry> {
        val db = readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            PasswordEntryContract.PasswordEntry.COLUMN_NAME_NAME,
            PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME,
            PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD
        )
        val cursor = db.query(
            PasswordEntryContract.PasswordEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val entries = mutableListOf<PasswordEntry>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(PasswordEntryContract.PasswordEntry.COLUMN_NAME_NAME))
                val username = getString(getColumnIndexOrThrow(PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME))
                val password = getString(getColumnIndexOrThrow(PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD))
                entries.add(PasswordEntry(id, name, username, password))
            }
        }
        cursor.close()
        return entries
    }



    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PasswordManager.db"
    }
}
