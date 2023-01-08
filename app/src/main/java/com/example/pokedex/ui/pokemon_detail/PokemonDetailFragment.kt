package com.example.pokedex.ui.pokemon_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.utils.Constants
import com.example.pokedex.utils.NetworkManager
import kotlinx.coroutines.launch

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private var pokemonId = 0
    private lateinit var viewModel : PokemonDetailViewModel
    private val networkManager = NetworkManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonId = arguments?.getInt(Constants.POKEMON_ID) ?: 0
        viewModel = ViewModelProvider(requireActivity())[PokemonDetailViewModel::class.java]
        if (pokemonId > 0) {
            lifecycleScope.launch{
                viewModel.getPokemonDetail(pokemonId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.detailProgressBar.visibility = View.VISIBLE
            } else {
                binding.detailProgressBar.visibility = View.INVISIBLE
                binding.linear.visibility = View.VISIBLE
            }
        }

        networkManager.networkStateManager(
            context = requireContext(),
            onAvailable = { lifecycleScope.launch { connected() }},
            onLost = { lifecycleScope.launch { disconnected() }})

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
    }
    private fun connected() {
        binding.linear.visibility = View.VISIBLE
        binding.noInternetConnectionLayoutPokemonDetail.visibility = View.GONE
    }

    private fun disconnected() {
        binding.linear.visibility = View.INVISIBLE
        binding.noInternetConnectionLayoutPokemonDetail.visibility = View.VISIBLE
    }
}




