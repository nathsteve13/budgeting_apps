package com.ubaya.budgetingapps.util

import android.content.Context
import androidx.room.Room
import com.ubaya.budgetingapps.model.AppDatabase

val DB_NAME = "expensetracker.db"

// sementara belum ada migration
// val MIGRATION_1_2 = object : Migration(1, 2) {
//     override fun migrate(database: SupportSQLiteDatabase) {
//         database.execSQL("ALTER TABLE your_table ADD COLUMN your_column TEXT")
//     }
// }

fun buildDb(context: Context): AppDatabase {
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, DB_NAME
    )
        //.addMigrations(MIGRATION_1_2) // nanti kalau ada migration tinggal aktifkan
        .fallbackToDestructiveMigration() // sementara pakai destructive migration dulu
        .build()

    return db
}
