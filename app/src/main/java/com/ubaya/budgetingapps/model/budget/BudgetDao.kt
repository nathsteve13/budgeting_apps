package com.ubaya.budgetingapps.model.budget

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {
    @Insert
    fun insert(budget: Budget): Long

    @Update
    fun updateBudget(budget: Budget)

    @Delete
    fun deleteBudget(budget: Budget)

    @Query("SELECT * FROM budget")
    fun selectAll(): List<Budget>

    @Query("SELECT * FROM budget WHERE uuid = :uuid")
    fun selectById(uuid: Int): Budget

}
