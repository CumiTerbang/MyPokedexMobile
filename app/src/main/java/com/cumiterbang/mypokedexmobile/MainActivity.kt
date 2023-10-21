package com.cumiterbang.mypokedexmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cumiterbang.mypokedexmobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setupBottomNavigation(){
        with(binding.navView){
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            this.setupWithNavController(navController)
        }
    }
}