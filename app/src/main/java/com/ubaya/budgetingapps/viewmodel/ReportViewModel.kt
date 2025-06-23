package com.ubaya.budgetingapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.budgetingapps.model.budget.Budget
import com.ubaya.budgetingapps.model.budget.BudgetDatabase
import com.ubaya.budgetingapps.model.expense.Expense
import com.ubaya.budgetingapps.model.expense.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReportViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val budgetLD = MutableLiveData<List<Budget>>()   // LiveData untuk menyimpan daftar budget
    val expenseLD = MutableLiveData<List<Expense>>() // LiveData untuk menyimpan daftar pengeluaran

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    // Ambil data dari database
    fun refresh() {
        launch {
            val budgetDb = BudgetDatabase.getInstance(getApplication())
            val expenseDb = ExpenseDatabase.getInstance(getApplication())

            budgetLD.postValue(budgetDb.budgetDao().selectAll())
            expenseLD.postValue(expenseDb.expenseDao().getAllExpenses())
        }
    }
}
