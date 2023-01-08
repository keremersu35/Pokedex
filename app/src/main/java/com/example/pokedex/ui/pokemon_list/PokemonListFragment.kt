package com.example.pokedex.ui.pokemon_list

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.ui.pokemon_list.adapter.LoadMoreAdapter
import com.example.pokedex.ui.pokemon_list.adapter.PokemonListAdapter
import com.example.pokedex.utils.Constants
import com.example.pokedex.utils.NetworkManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private var pokemonsAdapter = PokemonListAdapter()
    private val networkManager = NetworkManager()
    lateinit var viewModel: PokemonListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PokemonListViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                pokemonsAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Constants.hasInternet) connected()
        else disconnected()

        networkManager.networkStateManager(
            context = requireContext(),
            onAvailable = { lifecycleScope.launch { reconnected() } }
        )

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
    }

    private fun disconnected() {
        binding.pokemonRv.visibility = View.INVISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun reconnected() {
        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                pokemonsAdapter.submitData(lifecycle, it)
            }
        }
        pokemonsAdapter.notifyDataSetChanged()
        binding.pokemonRv.visibility = View.VISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.GONE
    }

    private fun connected() {
        binding.pokemonRv.visibility = View.VISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.GONE
    }
}



