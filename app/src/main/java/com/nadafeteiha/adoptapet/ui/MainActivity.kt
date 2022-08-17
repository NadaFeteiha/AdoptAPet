package com.nadafeteiha.adoptapet.ui

import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nadafeteiha.adoptapet.R
import com.nadafeteiha.adoptapet.base.BaseActivity
import com.nadafeteiha.adoptapet.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val inflate: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun setup() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//
//        binding.bottomNavigation.setOnItemSelectedListener { item ->
//            navHostFragment.navController.popBackStack(item.itemId, true)
//        }
        setupWithNavController(binding.bottomNavigation, navHostFragment.navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}