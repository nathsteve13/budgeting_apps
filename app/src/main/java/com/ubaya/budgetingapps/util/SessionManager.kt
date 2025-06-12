package com.ubaya.budgetingapps.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "USERNAME"
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }

    fun saveLoginSession(username: String) {
        prefs.edit().putString(USERNAME, username)
            .putBoolean(IS_LOGGED_IN, true)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }
}
