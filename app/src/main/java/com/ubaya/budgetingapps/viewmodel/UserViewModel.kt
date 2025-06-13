package com.ubaya.budgetingapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.budgetingapps.model.User
import com.ubaya.budgetingapps.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val loginResultLD = MutableLiveData<User?>()
    val registerResultLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun register(user: User) {
        launch {
            val db = buildDb(getApplication())
            val existingUser = db.userDao().getUserByUsername(user.username)

            if (existingUser == null) {
                db.userDao().insertUser(user)
                registerResultLD.postValue(true)
            } else {
                registerResultLD.postValue(false)
            }
        }
    }

    fun login(username: String, password: String) {
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().login(username, password)
            loginResultLD.postValue(user)
        }
    }
}
