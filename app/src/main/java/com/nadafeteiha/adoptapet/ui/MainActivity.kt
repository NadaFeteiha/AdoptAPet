package com.nadafeteiha.adoptapet.ui

import android.view.LayoutInflater
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.nadafeteiha.adoptapet.R
import com.nadafeteiha.adoptapet.base.BaseActivity
import com.nadafeteiha.adoptapet.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val inflate: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.petFragment,
                R.id.searchFragment,
                R.id.adaptionCenterFragment
            )
        )
    }

    override fun start() {
        installSplashScreen()
    }

    override fun setup() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavBar()
        setupWithNavController(
            binding.topAppBar,
            navHostFragment.navController,
            appBarConfiguration
        )
        setToolBarVisibility()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun bottomNavBar() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            navController.popBackStack(item.itemId, inclusive = false)
            true
        }
    }

    private fun setToolBarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment, R.id.petDetailsFragment -> {
                    binding.topAppBar.visibility = View.GONE
                }
                else -> {
                    binding.topAppBar.visibility = View.VISIBLE
                }
            }
        }
    }

}