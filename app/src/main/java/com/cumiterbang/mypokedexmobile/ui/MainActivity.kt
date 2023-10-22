package com.cumiterbang.mypokedexmobile.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cumiterbang.mypokedexmobile.R
import com.cumiterbang.mypokedexmobile.databinding.ActivityMainBinding
import com.cumiterbang.mypokedexmobile.ui.home_menu.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        observeGoCatchPokemonFromHome()
    }

    private fun setupBottomNavigation() {
        with(binding.navView) {
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            this.setupWithNavController(navController)
        }
    }

    private fun observeGoCatchPokemonFromHome() {
        homeViewModel.goCatchPokemon.observe(this) {
            if (it) {
                binding.navView.selectedItemId = R.id.navigation_catch
                homeViewModel.setGoCactchPokemon(false)
            }
        }
    }
}