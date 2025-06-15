package com.ubaya.budgetingapps.model.budget

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Budget(
    var name: String,
    var amount: String,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)
