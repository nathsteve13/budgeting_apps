package com.ubaya.budgetingapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ubaya.budgetingapps.model.User
import com.ubaya.budgetingapps.util.buildDb
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    fun insertUser(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val db = buildDb(getApplication())
            try {
                db.userDao().insertUser(user)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun login(username: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val db = buildDb(getApplication())
            val user = db.userDao().login(username, password)
            onResult(user)
        }
    }
}
