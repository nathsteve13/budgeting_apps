package com.ubaya.budgetingapps.model.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uuid")
    var uuid: Int = 0,
    @ColumnInfo(name = "date")
    var date: Long,
    @ColumnInfo(name = "amount")
    var amount: Int,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "budgetId")
    var budgetId: Int,
    @ColumnInfo(name = "budgetName")
    var budgetName: String
)