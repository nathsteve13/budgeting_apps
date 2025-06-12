package com.ubaya.budgetingapps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment

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
