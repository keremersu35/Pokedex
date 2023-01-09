package com.example.pokedex.ui.pokemon_list

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.network.NetworkManager
import com.example.pokedex.ui.pokemon_list.adapter.LoadMoreAdapter
import com.example.pokedex.ui.pokemon_list.adapter.PokemonListAdapter
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private var pokemonsAdapter = PokemonListAdapter()
    private val networkManager = NetworkManager()
    private lateinit var viewModel: PokemonListViewModel
    private var hasInternet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkDrawOverlayPermission()
        viewModel = ViewModelProvider(requireActivity())[PokemonListViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                pokemonsAdapter.submitData(lifecycle, it)
            }
        }
        hasInternet = networkManager.isNetworkAvailable(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasInternet) disconnected()

        lifecycleScope.launchWhenCreated {
            pokemonsAdapter.loadStateFlow.collect {
                val state = it.refresh
                binding.prgBarMovies.isVisible = state is LoadState.Loading
            }
        }

        binding.pokemonRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pokemonsAdapter
        }

        binding.pokemonRv.adapter = pokemonsAdapter.withLoadStateFooter(
            LoadMoreAdapter {
                pokemonsAdapter.retry()
            }
        )


        networkManager.networkStateManager(requireContext(),
            onLost = { },
            onAvailable = {
                pokemonsAdapter.retry(); lifecycleScope.launch { connected() } })

    }

    private fun disconnected() {
        binding.pokemonRv.visibility = View.INVISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.VISIBLE
    }

    private fun connected() {
        binding.pokemonRv.visibility = View.VISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()

        networkManager.networkStateManager(requireContext(),
            onLost = { },
            onAvailable = { pokemonsAdapter.retry(); lifecycleScope.launch { connected() } })

    }

    override fun onResume() {
        super.onResume()
        checkDrawOverlayPermission()

        networkManager.networkStateManager(requireContext(),
            onLost = { },
            onAvailable = { pokemonsAdapter.retry(); lifecycleScope.launch { connected() } })
    }

    private fun checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                if (!Settings.canDrawOverlays(requireActivity()))
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_pokemonListFragment_to_permissionFragment)
            }
        }
    }
}



