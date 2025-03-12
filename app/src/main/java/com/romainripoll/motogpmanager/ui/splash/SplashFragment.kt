package com.romainripoll.motogpmanager.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.romainripoll.motogpmanager.R
import com.romainripoll.motogpmanager.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SplashViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, 
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Pour les tests, nous allons montrer les boutons après un délai
        showStartOptions()
        
        // Configurer les listeners pour les boutons
        binding.btnNewGame.setOnClickListener {
            // Naviguer vers l'écran de création d'équipe
            findNavController().navigate(R.id.action_splash_to_teamCreation)
        }
        
        binding.btnContinue.setOnClickListener {
            // Naviguer directement vers l'écran principal
            // Dans la version finale, on vérifiera d'abord si une partie existe
            findNavController().navigate(R.id.action_splash_to_main)
        }
        
        /* Version finale (à implémenter plus tard) :
        
        // Observer le chargement des données
        viewModel.hasSavedGame.observe(viewLifecycleOwner) { hasSavedGame ->
            if (hasSavedGame) {
                // Montrer les options de démarrage
                showStartOptions()
            } else {
                // Pas de partie sauvegardée, rediriger directement vers la création d'équipe
                findNavController().navigate(R.id.action_splash_to_teamCreation)
            }
        }
        
        // Vérifier s'il existe une partie sauvegardée
        viewModel.checkForSavedGame()
        */
    }
    
    private fun showStartOptions() {
        // Simuler un chargement pour l'écran de démarrage
        lifecycleScope.launch {
            delay(2000) // Attendre 2 secondes pour simuler le chargement
            
            // Masquer les éléments de chargement
            binding.progressBar.visibility = View.GONE
            binding.tvLoading.visibility = View.GONE
            
            // Afficher les boutons
            binding.btnNewGame.visibility = View.VISIBLE
            
            // Pour les tests, nous affichons toujours le bouton Continuer
            // Dans la version finale, ce bouton ne serait affiché que s'il existe une partie sauvegardée
            binding.btnContinue.visibility = View.VISIBLE
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
