package com.ubaya.budgetingapps.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.budgetingapps.model.budget.Budget
import com.ubaya.budgetingapps.model.budget.BudgetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BudgetViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val budgetLD = MutableLiveData<List<Budget>>()
    val budgetSingleLD = MutableLiveData<Budget>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = BudgetDatabase(getApplication())
            budgetLD.postValue(db.budgetDao().selectAll())
        }
    }

    fun load(uuid: Int) {
        launch {
            val db = BudgetDatabase(getApplication())
            budgetSingleLD.postValue(db.budgetDao().selectById(uuid))
        }
    }

    fun insert(budget: Budget) {
        launch {
            val id = BudgetDatabase(getApplication()).budgetDao().insert(budget)
            budget.uuid = id.toInt()
            refresh()
        }
    }


    fun update(budget: Budget) {
        launch {
            BudgetDatabase(getApplication()).budgetDao().updateBudget(budget)
            refresh()
        }
    }
}
