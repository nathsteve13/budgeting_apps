package com.ubaya.budgetingapps.model.report

import androidx.room.*

@Dao
interface ReportDao {
    @Insert
    fun insert(report: Report): Long

    @Update
    fun update(report: Report)

    @Delete
    fun delete(report: Report)

    @Query("SELECT * FROM report")
    fun selectAll(): List<Report>

    @Query("SELECT * FROM report WHERE budgetId = :budgetId")
    fun getByBudgetId(budgetId: Int): List<Report>

    @Query("SELECT SUM(CAST(used AS INTEGER)) FROM report WHERE budgetId = :budgetId")
    fun getTotalUsedByBudgetId(budgetId: Int): Int?

    @Query("SELECT * FROM report WHERE id = :id")
    fun getById(id: Int): Report
}
