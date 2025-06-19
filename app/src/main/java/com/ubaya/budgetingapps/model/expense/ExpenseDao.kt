package com.ubaya.budgetingapps.model.expense

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense")
    fun getAllExpensesNow(): List<Expense>

    @Query("SELECT SUM(amount) FROM Expense WHERE budgetName = :budgetName")
    fun getTotalExpensesForBudget(budgetName: String): Int?

    @Insert
    fun insertExpense(expense: Expense): Long

    @Query("SELECT * FROM Expense ORDER BY date DESC")
    fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM Expense WHERE uuid = :id")
    fun getExpenseById(id: Int): Expense

    @Delete
    fun deleteExpense(expense: Expense)

}