package com.ubaya.budgetingapps.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.budgetingapps.R
import com.ubaya.budgetingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView
        binding.bottomNavigation.setupWithNavController(navController)

        // Optional: setup ActionBar (jika pakai toolbar di MainActivity)
        // NavigationUI.setupActionBarWithNavController(this, navController)
    }

    // Optional: handle tombol back di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
