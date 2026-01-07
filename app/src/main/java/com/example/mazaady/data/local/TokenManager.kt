package com.example.mazaady.data.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor() {

    private var authToken: String? = null

    fun saveToken(token: String) {
        authToken = token
    }

    fun getToken(): String? = authToken

    fun clearToken() {
        authToken = null
    }

    fun hasToken(): Boolean = authToken != null
}