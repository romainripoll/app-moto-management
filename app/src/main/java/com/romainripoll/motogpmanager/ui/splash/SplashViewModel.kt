package com.romainripoll.motogpmanager.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romainripoll.motogpmanager.MotoGpManagerApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel : ViewModel() {

    private val _hasSavedGame = MutableLiveData<Boolean>()
    val hasSavedGame: LiveData<Boolean> = _hasSavedGame
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Vérifie s'il existe une partie sauvegardée dans la base de données
     */
    fun checkForSavedGame() {
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val database = MotoGpManagerApp.instance.database
                    val gameState = database.gameStateDao().getGameStates()
                    gameState.isNotEmpty()
                }
                
                _hasSavedGame.value = result
            } catch (e: Exception) {
                // En cas d'erreur, on considère qu'il n'y a pas de partie sauvegardée
                _hasSavedGame.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
