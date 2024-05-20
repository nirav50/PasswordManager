package com.example.passwordmanager.Database

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

private const val AES_ALGORITHM = "AES"
private const val ENCRYPTION_KEY = "YourEncryptionKey" // This should be a securely generated key

fun encrypt(text: String): String {
    val keySpec = SecretKeySpec(ENCRYPTION_KEY.toByteArray(), AES_ALGORITHM)
    val cipher = Cipher.getInstance(AES_ALGORITHM)
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)
    val encryptedBytes = cipher.doFinal(text.toByteArray())
    return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
}
