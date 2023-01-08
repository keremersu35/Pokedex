package com.example.pokedex.ui.pokemon_list

import android.content.ClipData.Item
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.ui.pokemon_list.adapter.LoadMoreAdapter
import com.example.pokedex.ui.pokemon_list.adapter.PokemonListAdapter
import com.example.pokedex.utils.NetworkManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private var pokemonsAdapter = PokemonListAdapter()
    private lateinit var state: LoadState
    private val networkManager = NetworkManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[PokemonListViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                pokemonsAdapter.submitData(lifecycle, it)
            }
        }

        lifecycleScope.launchWhenCreated {
            pokemonsAdapter.loadStateFlow.collect {
                state = it.refresh
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

        networkManager.networkStateManager(
            context = requireContext(),
            onLost = { lifecycleScope.launch{ disconnected() }},
            onAvailable = { lifecycleScope.launch{ connected() }}
        )
    }

    private fun disconnected() {
        binding.pokemonRv.visibility = View.INVISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.VISIBLE
    }

    private fun connected() {
        binding.prgBarMovies.isVisible = state is LoadState.Loading
        binding.pokemonRv.visibility = View.VISIBLE
        binding.noInternetConnectionLayoutPokemonList.visibility = View.GONE
    }
}