package com.ubaya.budgetingapps.model.report

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Report(
    var budgetId: Int,
    var used: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
