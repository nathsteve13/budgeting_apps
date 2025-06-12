package com.ubaya.budgetingapps.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name: String,
    @PrimaryKey val username: String,
    val password: String
)
