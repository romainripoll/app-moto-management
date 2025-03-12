package com.romainripoll.motogpmanager.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.romainripoll.motogpmanager.R
import com.romainripoll.motogpmanager.databinding.ActivityMainBinding
import com.romainripoll.motogpmanager.ui.main.bikes.BikesFragment
import com.romainripoll.motogpmanager.ui.main.races.RacesFragment
import com.romainripoll.motogpmanager.ui.main.riders.RidersFragment
import com.romainripoll.motogpmanager.ui.main.standings.StandingsFragment
import com.romainripoll.motogpmanager.ui.main.team.TeamFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Set up bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_team -> loadFragment(TeamFragment())
                R.id.nav_riders -> loadFragment(RidersFragment())
                R.id.nav_bikes -> loadFragment(BikesFragment())
                R.id.nav_races -> loadFragment(RacesFragment())
                R.id.nav_standings -> loadFragment(StandingsFragment())
                else -> false
            }
        }

        // Set default fragment
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_team
        }

        // Observe game state changes
        viewModel.gameState.observe(this) { gameState ->
            // Update UI based on game state changes
            supportActionBar?.title = gameState.teamName
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
}