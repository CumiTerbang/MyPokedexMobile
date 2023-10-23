package com.cumiterbang.mypokedexmobile.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
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
        animateExitSplashSceen(splashScreen)
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

    private fun animateExitSplashSceen(splashscreen: SplashScreen){
        splashscreen.setOnExitAnimationListener { splashScreenView ->
            val anim = ValueAnimator.ofFloat(1f, 7f)
            anim.duration = 500
            anim.addUpdateListener { animation ->
                splashScreenView.view.setScaleX(animation.animatedValue as Float)
                splashScreenView.view.setScaleY(animation.animatedValue as Float)
            }

            anim.addListener(object:AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    splashScreenView.remove()
                }
            })

            Thread.sleep(1000)
            anim.start()
        }
    }
}