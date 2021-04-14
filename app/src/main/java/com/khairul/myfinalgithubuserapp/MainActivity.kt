package com.khairul.myfinalgithubuserapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.khairul.myfinalgithubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    private lateinit var config: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        controller = findNavController(R.id.nav_host)
        NavigationUI.setupWithNavController(binding.bottomNavView, controller)
        config = AppBarConfiguration(controller.graph)
        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_details) binding.bottomNavView.visibility = View.GONE
            else binding.bottomNavView.visibility = View.VISIBLE
        }
        setupActionBarWithNavController(controller, config)
    }

    override fun onSupportNavigateUp(): Boolean {
        controller.navigateUp(config)
        return super.onSupportNavigateUp()
    }


}