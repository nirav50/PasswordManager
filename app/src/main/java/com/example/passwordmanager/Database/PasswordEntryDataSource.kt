package com.example.passwordmanager.Database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.passwordmanager.Models.PasswordEntry
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class PasswordEntryDataSource(context: Context) {

    private val dbHelper = PasswordEntryDbHelper(context)

    // Encryption related constants
    private val AES_ALGORITHM = "AES"
    private val ENCRYPTION_KEY = "YourEncryptionKey" // Replace with a securely generated key

    fun insertPasswordEntry(name: String, username: String, password: String): Long {
        val db = dbHelper.writableDatabase
        val encryptedPassword = encrypt(password)
        val values = ContentValues().apply {
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_NAME, name)
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME, username)
            put(PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD, encryptedPassword)
        }
        return db.insert(PasswordEntryContract.PasswordEntry.TABLE_NAME, null, values)
    }

    fun getAllPasswordEntries(): List<PasswordEntry> {
        val db = dbHelper.readableDatabase
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
                val username =
                    getString(getColumnIndexOrThrow(PasswordEntryContract.PasswordEntry.COLUMN_NAME_USERNAME))
                val encryptedPassword =
                    getString(getColumnIndexOrThrow(PasswordEntryContract.PasswordEntry.COLUMN_NAME_PASSWORD))
                val password = decrypt(encryptedPassword)
                entries.add(PasswordEntry(id,name, username, password))
            }
        }
        cursor.close()
        return entries
    }

    private fun encrypt(text: String): String {
        val keySpec = SecretKeySpec(ENCRYPTION_KEY.toByteArray(), AES_ALGORITHM)
        val cipher = Cipher.getInstance(AES_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }

    private fun decrypt(encryptedText: String): String {
        val keySpec = SecretKeySpec(ENCRYPTION_KEY.toByteArray(), AES_ALGORITHM)
        val cipher = Cipher.getInstance(AES_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decodedBytes = android.util.Base64.decode(encryptedText, android.util.Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
