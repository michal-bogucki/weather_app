package com.weatherapplication.core.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weatherapplication.R
import com.weatherapplication.core.base.BaseActivity
import com.weatherapplication.core.extension.gone
import com.weatherapplication.core.extension.visible
import com.weatherapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBottomNavView()
    }
    var cityId: String? = null
    private fun initBottomNavView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.itemIconTintList = null

        navController.addOnDestinationChangedListener { _, destination, bundle ->

            when (destination.id) {
                R.id.searchCityFragment -> {
                    binding.bottomNavigationView.gone()
                    binding.divider.gone()
                }
                R.id.chooseGetCityFragment -> {
                    binding.bottomNavigationView.gone()
                    binding.divider.gone()
                }
                else -> {
                    binding.bottomNavigationView.visible()
                    binding.divider.visible()
                }
            }

            when (destination.id) {
                R.id.weatherNewFragment -> {
                    cityId = bundle?.getString("cityId")
                }
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
