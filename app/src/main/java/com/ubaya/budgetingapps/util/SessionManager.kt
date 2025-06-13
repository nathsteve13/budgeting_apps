package com.ubaya.budgetingapps.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "USERNAME"
        const val IS_LOGIN = "IS_LOGIN"
    }

    fun saveLoginSession(username: String) {
        prefs.edit().apply {
            putString(USERNAME, username)
            putBoolean(IS_LOGIN, true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    fun getUsername(): String? {
        return prefs.getString(USERNAME, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
