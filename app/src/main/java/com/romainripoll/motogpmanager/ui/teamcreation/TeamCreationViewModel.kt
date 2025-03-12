package com.romainripoll.motogpmanager.ui.teamcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainripoll.motogpmanager.MotoGpManagerApp
import com.romainripoll.motogpmanager.data.model.BikeManufacturer
import com.romainripoll.motogpmanager.data.model.Bike
import com.romainripoll.motogpmanager.data.model.Team
import com.romainripoll.motogpmanager.data.model.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class TeamCreationViewModel : ViewModel() {

    private val _teamCreated = MutableLiveData<Boolean>()
    val teamCreated: LiveData<Boolean> = _teamCreated

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Propriétés pour la création d'équipe
    private var teamName: String = ""
    private var teamColor: String = "#FF0000"  // Rouge par défaut
    private var bikeManufacturer: BikeManufacturer = BikeManufacturer.DUCATI
    private var difficulty: Int = 2  // 1=Facile, 2=Normal, 3=Difficile
    private var budget: Int = 1_000_000

    fun setTeamName(name: String) {
        teamName = name
    }

    fun setSelectedColor(hexCode: String) {
        teamColor = hexCode
    }

    fun setBikeManufacturer(manufacturer: BikeManufacturer) {
        bikeManufacturer = manufacturer
    }

    fun setDifficulty(level: Int) {
        difficulty = level
    }

    fun setBudget(amount: Int) {
        budget = amount
    }

    fun createTeam() {
        if (teamName.isBlank()) {
            _errorMessage.value = "Veuillez entrer un nom d'équipe valide"
            return
        }

        viewModelScope.launch {
            try {
                // Création de l'objet Team
                val team = Team(
                    id = UUID.randomUUID().toString(),
                    name = teamName,
                    mainColor = teamColor,
                    budget = budget,
                    reputation = calculateInitialReputation(),
                    riderIds = emptyList()
                )

                // Création de la moto initiale
                val bike = Bike(
                    id = UUID.randomUUID().toString(),
                    manufacturer = bikeManufacturer,
                    acceleration = bikeManufacturer.baseAcceleration,
                    topSpeed = bikeManufacturer.baseTopSpeed,
                    handling = bikeManufacturer.baseHandling,
                    braking = bikeManufacturer.baseBraking,
                    reliability = bikeManufacturer.baseReliability
                )

                // Création d'un nouveau GameState
                val gameState = GameState(
                    id = UUID.randomUUID().toString(),
                    teamId = team.id,
                    currentDay = 1,
                    difficulty = difficulty,
                    bikes = listOf(bike),
                    seasonPoints = 0,
                    completedRaces = 0
                )

                // Sauvegarde en base de données
                saveGameData(team, bike, gameState)

            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la création de l'équipe: ${e.message}"
            }
        }
    }

    private fun calculateInitialReputation(): Int {
        // La réputation initiale varie en fonction de la difficulté
        return when (difficulty) {
            1 -> 60  // Facile
            2 -> 40  // Normal
            3 -> 20  // Difficile
            else -> 40
        }
    }

    private suspend fun saveGameData(team: Team, bike: Bike, gameState: GameState) {
        withContext(Dispatchers.IO) {
            try {
                val database = MotoGpManagerApp.instance.database
                
                // Insertion des données dans la base
                database.teamDao().insertTeam(team)
                database.bikeDao().insertBike(bike)
                database.gameStateDao().insertGameState(gameState)
                
                _teamCreated.postValue(true)
            } catch (e: Exception) {
                _errorMessage.postValue("Erreur lors de la sauvegarde: ${e.message}")
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
