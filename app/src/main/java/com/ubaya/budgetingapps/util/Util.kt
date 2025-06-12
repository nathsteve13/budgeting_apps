package com.ubaya.budgetingapps.util

import android.content.Context
import androidx.room.Room
import com.ubaya.budgetingapps.model.AppDatabase

val DB_NAME = "budgetingapp.db"

fun buildDb(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DB_NAME
    ).build()
}
