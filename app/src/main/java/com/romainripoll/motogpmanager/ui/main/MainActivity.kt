package com.romainripoll.motogpmanager.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.romainripoll.motogpmanager.R
import com.romainripoll.motogpmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Configuration du NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // Configuration de la barre d'action
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        
        // Mise à jour du titre en fonction de la destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val title = when (destination.id) {
                R.id.splashFragment -> ""  // Pas de titre pour l'écran de démarrage
                R.id.teamCreationFragment -> getString(R.string.title_team_creation)
                R.id.mainFragment -> getString(R.string.title_main)
                else -> getString(R.string.app_name)
            }
            supportActionBar?.title = title
            
            // Masquer la barre d'action sur l'écran de démarrage
            if (destination.id == R.id.splashFragment) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
