package com.ubaya.budgetingapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.budgetingapps.model.budget.BudgetDatabase
import com.ubaya.budgetingapps.model.expense.Expense
import com.ubaya.budgetingapps.model.expense.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ExpenseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val expenseLD = MutableLiveData<List<Expense>>()
    val expenseSingleLD = MutableLiveData<Expense>()
    val totalExpenseLiveData = MutableLiveData<Int>()

    private val db = ExpenseDatabase(application)

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = ExpenseDatabase(getApplication())
            expenseLD.postValue(db.expenseDao().getAllExpenses())
        }
    }

    fun load(uuid: Int) {
        launch {
            expenseSingleLD.postValue(db.expenseDao().getExpenseById(uuid))
        }
    }

    fun insert(expense: Expense) {
        launch {
            val id = db.expenseDao().insertExpense(expense)
            expense.uuid = id.toInt()
            refresh()
        }
    }

    fun loadTotalExpensesForBudget(budgetName: String) {
        launch {
            val total = ExpenseDatabase(getApplication()).expenseDao()
                .getTotalExpensesForBudget(budgetName) ?: 0
            totalExpenseLiveData.postValue(total)
        }
    }
}
