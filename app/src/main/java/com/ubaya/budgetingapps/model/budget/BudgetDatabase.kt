package com.ubaya.budgetingapps.model.budget

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [Budget::class], version = 2)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile private var instance: BudgetDatabase? = null
        private val LOCK = Any()

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BudgetDatabase::class.java,
            "budgetdb"
        ).fallbackToDestructiveMigration()
            .build()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        fun getInstance(context: Context): BudgetDatabase {
            return invoke(context)
        }
    }
}
