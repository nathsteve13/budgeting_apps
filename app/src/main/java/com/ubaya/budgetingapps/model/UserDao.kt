package com.ubaya.budgetingapps.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertUser(vararg user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    fun getUserByUsername(username: String): User

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun login(username: String, password: String): User
}
