package com.example.pokedex.ui.pokemon_detail

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.network.NetworkManager
import com.example.pokedex.utils.Constants
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokemonId = 0
    private lateinit var viewModel: PokemonDetailViewModel
    private val networkManager = NetworkManager()
    private var hasInternet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        checkDrawOverlayPermission()
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonId = arguments?.getInt(Constants.POKEMON_ID) ?: 0
        viewModel = ViewModelProvider(requireActivity())[PokemonDetailViewModel::class.java]
        if (pokemonId > 0) {
            lifecycleScope.launch { viewModel.getPokemonDetail(pokemonId) }
        }
        hasInternet = networkManager.isNetworkAvailable(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(hasInternet) {
            viewModel.loading.observe(viewLifecycleOwner) {
                binding.apply {
                    if (it) {
                        detailProgressBar.visibility = View.VISIBLE
                    } else {
                        detailProgressBar.visibility = View.INVISIBLE
                        linear.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { pokemon ->
            binding.pokemonDetailImage.load(pokemon.getImage()) {
                crossfade(true)
                placeholder(R.drawable.pokeball)
            }
            binding.pokemon = pokemon
            binding.overlayButton.setOnClickListener {
                val window = Window(requireActivity(), pokemon)
                window.open()
            }
        }

        if (!hasInternet) disconnected()
    }

    private fun disconnected() {
        binding.apply {
            linear.isVisible = false
            noInternetConnectionLayoutPokemonDetail.visibility = View.VISIBLE
            detailProgressBar.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        checkDrawOverlayPermission()
    }

    private fun checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                if (!Settings.canDrawOverlays(requireActivity()))
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_pokemonDetailFragment_to_permissionFragment)
            }
        }
    }
}