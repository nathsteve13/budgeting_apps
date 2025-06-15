package com.ubaya.budgetingapps.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ubaya.budgetingapps.R
import com.ubaya.budgetingapps.databinding.ActivityAuthBinding
import com.ubaya.budgetingapps.util.SessionManager
import com.ubaya.budgetingapps.view.MainActivity


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(applicationContext)
        if (session.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        navController = (supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment).navController
        //NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
