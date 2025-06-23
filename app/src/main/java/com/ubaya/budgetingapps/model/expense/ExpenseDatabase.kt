package com.ubaya.budgetingapps.model.expense

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

    @Database(entities = [Expense::class], version = 1)
    abstract class ExpenseDatabase : RoomDatabase() {
        abstract fun expenseDao(): ExpenseDao

        companion object {
            @Volatile private var instance: ExpenseDatabase? = null
            private val LOCK = Any()

            fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "expensedb"
            ).fallbackToDestructiveMigration()
                .build()

            operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }

            fun getInstance(context: Context): ExpenseDatabase {
                return invoke(context)
            }
        }
    }
