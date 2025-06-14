package com.ubaya.budgetingapps.util

import android.content.Context
import android.content.SharedPreferences
import com.ubaya.budgetingapps.model.User

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        const val USERNAME = "USERNAME"
        const val IS_LOGIN = "IS_LOGIN"
        const val PASSWORD = "PASSWORD"
    }

    fun saveLoginSession(username: String, password: String) {
        prefs.edit().apply {
            putString(USERNAME, username)
            putString(PASSWORD, password)
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

    fun getUsernameProfile(): User {
        val username = prefs.getString(USERNAME, "") ?: ""
        return User(username = username, password = "", name = "")
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun savePassBaru(newPassword: String){
        prefs.edit().putString(PASSWORD, newPassword).apply()
    }

    fun getPassword(): String? {
        return prefs.getString(PASSWORD, "default")
    }

    fun getAllPrefs(): Map<String, *> {
        return prefs.all
    }
}
