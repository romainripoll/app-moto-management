package com.romainripoll.motogpmanager.ui.teamcreation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.Slider
import com.romainripoll.motogpmanager.R
import com.romainripoll.motogpmanager.databinding.FragmentTeamCreationBinding
import com.romainripoll.motogpmanager.data.model.Team
import com.romainripoll.motogpmanager.data.model.Bike
import com.romainripoll.motogpmanager.data.model.BikeManufacturer

class TeamCreationFragment : Fragment() {

    private var _binding: FragmentTeamCreationBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TeamCreationViewModel by viewModels()
    
    private val colorOptions = listOf(
        ColorOption("#FF0000", "Rouge"),
        ColorOption("#0000FF", "Bleu"),
        ColorOption("#FFFF00", "Jaune"),
        ColorOption("#00FF00", "Vert"),
        ColorOption("#FF00FF", "Rose"),
        ColorOption("#00FFFF", "Cyan"),
        ColorOption("#FF8000", "Orange"),
        ColorOption("#800080", "Violet"),
        ColorOption("#000000", "Noir")
    )
    
    private var selectedColorIndex = 0
    private val colorViews = mutableListOf<CardView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupColorOptions()
        setupDifficultySlider()
        setupCreateButton()
        
        observeViewModel()
    }
    
    private fun setupColorOptions() {
        val colorContainer = binding.colorOptionsContainer
        
        // Création dynamique des options de couleur
        colorOptions.forEachIndexed { index, colorOption ->
            val cardView = CardView(requireContext()).apply {
                radius = resources.getDimension(R.dimen.color_option_radius) // Vous devrez ajouter cette dimension
                setCardBackgroundColor(Color.parseColor(colorOption.hexCode))
                contentPadding = 0
                layoutParams = ViewGroup.MarginLayoutParams(
                    resources.getDimensionPixelSize(R.dimen.color_option_size), // Vous devrez ajouter cette dimension
                    resources.getDimensionPixelSize(R.dimen.color_option_size)
                ).apply {
                    marginEnd = resources.getDimensionPixelSize(R.dimen.color_option_margin) // Vous devrez ajouter cette dimension
                }
                
                // Bordure pour la couleur sélectionnée
                elevation = if (index == selectedColorIndex) 8f else 0f
                
                setOnClickListener {
                    selectColor(index)
                }
            }
            
            colorContainer.addView(cardView)
            colorViews.add(cardView)
        }
    }
    
    private fun selectColor(index: Int) {
        // Mise à jour de la couleur sélectionnée
        colorViews[selectedColorIndex].elevation = 0f
        selectedColorIndex = index
        colorViews[selectedColorIndex].elevation = 8f
        
        // Mise à jour du ViewModel
        viewModel.setSelectedColor(colorOptions[index].hexCode)
    }
    
    private fun setupDifficultySlider() {
        binding.sliderDifficulty.addOnChangeListener { _, value, _ ->
            val difficultyText = when (value.toInt()) {
                1 -> "Facile"
                2 -> "Normal"
                3 -> "Difficile"
                else -> "Normal"
            }
            binding.tvDifficultyValue.text = difficultyText
            
            // Mise à jour du budget en fonction de la difficulté
            val budget = when (value.toInt()) {
                1 -> 1_500_000
                2 -> 1_000_000
                3 -> 500_000
                else -> 1_000_000
            }
            
            binding.tvBudgetValue.text = String.format("%,d €", budget)
            viewModel.setDifficulty(value.toInt())
            viewModel.setBudget(budget)
        }
    }
    
    private fun setupCreateButton() {
        binding.btnCreateTeam.setOnClickListener {
            val teamName = binding.etTeamName.text.toString().trim()
            
            if (teamName.isEmpty()) {
                binding.tilTeamName.error = "Veuillez entrer un nom d'équipe"
                return@setOnClickListener
            }
            
            // Récupération de la marque de moto sélectionnée
            val bikeManufacturer = when (binding.rgBikeBrands.checkedRadioButtonId) {
                R.id.rbDucati -> BikeManufacturer.DUCATI
                R.id.rbHonda -> BikeManufacturer.HONDA
                R.id.rbYamaha -> BikeManufacturer.YAMAHA
                R.id.rbKTM -> BikeManufacturer.KTM
                R.id.rbAprilia -> BikeManufacturer.APRILIA
                R.id.rbSuzuki -> BikeManufacturer.SUZUKI
                else -> BikeManufacturer.DUCATI
            }
            
            viewModel.setTeamName(teamName)
            viewModel.setBikeManufacturer(bikeManufacturer)
            
            // Création de l'équipe
            viewModel.createTeam()
        }
    }
    
    private fun observeViewModel() {
        viewModel.teamCreated.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Navigation vers l'écran principal
                Toast.makeText(requireContext(), "Équipe créée avec succès !", Toast.LENGTH_SHORT).show()
                // TODO: Naviguer vers l'écran principal
                // findNavController().navigate(TeamCreationFragmentDirections.actionTeamCreationToMain())
            }
        }
        
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                viewModel.clearErrorMessage()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    data class ColorOption(val hexCode: String, val name: String)
}
