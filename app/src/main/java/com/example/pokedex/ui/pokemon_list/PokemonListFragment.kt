package com.example.pokedex.ui.pokemon_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.ui.pokemon_list.adapter.PokemonListAdapter
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.pokemonRv.layoutManager = layoutManager

        val viewModel = ViewModelProvider(requireActivity())[PokemonListViewModel::class.java]

        lifecycleScope.launch {
            viewModel.uiState.observe(viewLifecycleOwner) {
                binding.pokemonRv.adapter = PokemonListAdapter(it.data?.results ?: emptyList())
            }
        }
    }
}