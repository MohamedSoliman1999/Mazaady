package com.example.mazaady.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFS_FILE_NAME = "secure_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
    }

    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedPrefs: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Save authentication token securely
     */
    fun saveToken(token: String) {
        encryptedPrefs.edit {
            putString(KEY_AUTH_TOKEN, token)
        }
    }

    /**
     * Get authentication token
     */
    fun getToken(): String? {
        return encryptedPrefs.getString(KEY_AUTH_TOKEN, null)
    }

    /**
     * Save user ID
     */
    fun saveUserId(userId: String) {
        encryptedPrefs.edit {
            putString(KEY_USER_ID, userId)
        }
    }

    /**
     * Get user ID
     */
    fun getUserId(): String? {
        return encryptedPrefs.getString(KEY_USER_ID, null)
    }

    /**
     * Save user email
     */
    fun saveUserEmail(email: String) {
        encryptedPrefs.edit {
            putString(KEY_USER_EMAIL, email)
        }
    }

    /**
     * Get user email
     */
    fun getUserEmail(): String? {
        return encryptedPrefs.getString(KEY_USER_EMAIL, null)
    }

    /**
     * Clear all authentication data
     */
    fun clearToken() {
        encryptedPrefs.edit {
            remove(KEY_AUTH_TOKEN)
                .remove(KEY_USER_ID)
                .remove(KEY_USER_EMAIL)
        }
    }

    /**
     * Check if user is authenticated
     */
    fun hasToken(): Boolean {
        val token = getToken()
        return !token.isNullOrEmpty()
    }

    /**
     * Check if user session is valid
     */
    fun isAuthenticated(): Boolean {
        return hasToken() && getUserId() != null
    }
}