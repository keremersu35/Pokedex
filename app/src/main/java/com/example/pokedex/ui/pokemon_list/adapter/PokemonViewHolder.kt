package com.example.pokedex.ui.pokemon_list.adapter

import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemPokemonsRvBinding
import com.example.pokedex.domain.model.Result

class PokemonViewHolder(
    private val binding: ItemPokemonsRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: Result) {
        binding.apply {
            root.setOnClickListener { view ->
                Navigation.findNavController(view)
                    .navigate(
                        R.id.action_pokemonListFragment_to_pokemonDetailFragment,
                        bundleOf("pokemonId" to pokemon.getPokemonId())
                    )
            }
            binding.pokemon = pokemon
            pokemonImage.load(pokemon.getImageUrl()) {
                crossfade(true)
                placeholder(R.drawable.loading)
                transformations(CircleCropTransformation())
            }
        }
    }
}