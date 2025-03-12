package com.romainripoll.motogpmanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainripoll.motogpmanager.MotoGpManagerApp
import com.romainripoll.motogpmanager.data.local.GameDatabase
import com.romainripoll.motogpmanager.data.model.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val database: GameDatabase = MotoGpManagerApp.instance.database
    private val gameStateDao = database.gameStateDao()
    private val teamDao = database.teamDao()
    private val riderDao = database.riderDao()
    private val bikeDao = database.bikeDao()
    private val raceDao = database.raceDao()

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadGameState()
    }

    private fun loadGameState() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Load the game state from database
                val state = withContext(Dispatchers.IO) {
                    // Get the current game state or create a new one if none exists
                    val savedState = gameStateDao.getGameState()
                    savedState ?: createNewGameState()
                }
                _gameState.value = state
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun createNewGameState(): GameState {
        // Create a default game state for new games
        val defaultState = GameState(
            id = 1,
            seasonYear = 2025,
            currentRaceIndex = 0,
            teamName = "Your MotoGP Team",
            budget = 10000000,
            reputation = 50,
            researchPoints = 100,
            gameWeek = 1,
            staffQuality = 50
        )

        // Save it to database
        gameStateDao.insertGameState(defaultState)
        return defaultState
    }

    fun saveGameState() {
        viewModelScope.launch {
            _gameState.value?.let { state ->
                withContext(Dispatchers.IO) {
                    gameStateDao.updateGameState(state)
                }
            }
        }
    }

    fun updateTeamName(name: String) {
        _gameState.value = _gameState.value?.copy(teamName = name)
        saveGameState()
    }

    fun advanceWeek() {
        _gameState.value?.let { state ->
            _gameState.value = state.copy(gameWeek = state.gameWeek + 1)
            saveGameState()
        }
    }

    fun updateBudget(amount: Int) {
        _gameState.value?.let { state ->
            _gameState.value = state.copy(budget = state.budget + amount)
            saveGameState()
        }
    }
}