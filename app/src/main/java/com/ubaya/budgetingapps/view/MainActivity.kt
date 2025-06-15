package com.ubaya.budgetingapps.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ubaya.budgetingapps.R
import com.ubaya.budgetingapps.view.budgeting.BudgetingFragment
import com.ubaya.budgetingapps.view.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Default fragment saat dibuka
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, ExpenseTrackerFragment())
            .commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_expense_tracker -> ExpenseTrackerFragment()
                R.id.nav_budgeting -> BudgetingFragment()
                R.id.nav_report -> ReportFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, it)
                    .commit()
                true
            } ?: false
        }
    }
}
