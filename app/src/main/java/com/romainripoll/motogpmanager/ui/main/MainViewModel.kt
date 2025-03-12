package com.romainripoll.motogpmanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainripoll.motogpmanager.MotoGpManagerApp
import com.romainripoll.motogpmanager.data.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _teamData = MutableLiveData<Team?>()
    val teamData: LiveData<Team?> = _teamData
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    /**
     * Charge les données de l'équipe à partir de la base de données
     */
    fun loadTeamData() {
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val database = MotoGpManagerApp.instance.database
                    
                    // Récupérer l'état du jeu
                    val gameState = database.gameStateDao().getGameStates().firstOrNull()
                    
                    // Si un état de jeu existe, récupérer l'équipe associée
                    if (gameState != null) {
                        val team = database.teamDao().getTeamById(gameState.teamId)
                        _teamData.postValue(team)
                    } else {
                        // Aucun état de jeu trouvé
                        _teamData.postValue(null)
                    }
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Erreur lors du chargement des données: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
    
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
