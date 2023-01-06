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
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokemonId = 0
    private var isWindowState = false

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
                    if(isWindowState) {
                        window.open()
                        binding.overlayButton.text = "Kapat"
                        isWindowState = !isWindowState
                    }else{
                        window.close()
                        isWindowState = !isWindowState
                    }
                }
            }
        }
    }

    private fun startService(intent: Intent) {
        val intent = Intent(requireActivity(), ForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // check if the user has already granted
            // the Draw over other apps permission
            if (Settings.canDrawOverlays(requireActivity())) {
                // start the service based on the android version
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(requireActivity(), intent)
                } else {
                    startService(intent)
                }
            }
        } else {
            startService(intent)
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




