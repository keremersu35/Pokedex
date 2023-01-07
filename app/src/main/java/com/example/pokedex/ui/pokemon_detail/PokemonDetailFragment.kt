package com.example.pokedex.ui.pokemon_detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokemonId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonId = arguments?.getInt("pokemonId") ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[PokemonDetailViewModel::class.java]
        lifecycleScope.launch {
            viewModel.getPokemonDetail(pokemonId)
            viewModel.uiState.observe(viewLifecycleOwner) {
                println( it.message)
                val pokemon = it

                binding.pokemon = pokemon.data
                binding.pokemonDetailImage.load(pokemon.data?.getImage()) {
                    crossfade(true)
                    placeholder(R.drawable.pokeball)
                    transformations(CircleCropTransformation())
                }

                binding.overlayButton.setOnClickListener {
                    checkOverlayPermission()
                    val window = Window(requireActivity(), pokemon.data!!)
                    window.open()
                }
            }
        }
    }

    // method to ask user to grant the Overlay permission
    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireActivity())) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                ContextCompat.startActivity(requireActivity(), myIntent, Bundle.EMPTY);
            }
        }
    }
}




