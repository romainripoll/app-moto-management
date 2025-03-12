package com.romainripoll.motogpmanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.romainripoll.motogpmanager.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Observer les données de l'équipe
        viewModel.teamData.observe(viewLifecycleOwner) { team ->
            team?.let {
                binding.tvTeamName.text = it.name
                binding.tvBudget.text = "Budget: ${String.format("%,d €", it.budget)}"
            }
        }
        
        // Configurer les listeners des boutons
        setupButtonListeners()
        
        // Charger les données de l'équipe
        viewModel.loadTeamData()
    }
    
    private fun setupButtonListeners() {
        binding.btnManageRiders.setOnClickListener {
            // Rediriger vers l'écran de gestion des pilotes (à implémenter plus tard)
            Toast.makeText(requireContext(), "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnManageBikes.setOnClickListener {
            // Rediriger vers l'écran de gestion des motos (à implémenter plus tard)
            Toast.makeText(requireContext(), "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnStartRace.setOnClickListener {
            // Rediriger vers l'écran de course (à implémenter plus tard)
            Toast.makeText(requireContext(), "Fonctionnalité à venir", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
