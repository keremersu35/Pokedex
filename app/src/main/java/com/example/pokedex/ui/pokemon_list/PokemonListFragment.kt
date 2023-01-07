package com.example.pokedex.ui.pokemon_list

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
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.ui.pokemon_list.adapter.LoadMoreAdapter
import com.example.pokedex.ui.pokemon_list.adapter.PokemonListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    var pokemonsAdapter = PokemonListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[PokemonListViewModel::class.java]

/*        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                binding.pokemonRv.adapter = PokemonListAdapter(it ?: emptyList())
            }
        }*/
        lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collect {
                pokemonsAdapter.submitData(lifecycle, it)
            }
        }

/*        moviesAdapter.setOnItemClickListener {
            val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
            findNavController().navigate(direction)
        }*/

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


/*        lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) {
                binding.pokemonRv.adapter = PokemonListAdapter(it.data?.results ?: emptyList())
            }
        }*/
    }
}