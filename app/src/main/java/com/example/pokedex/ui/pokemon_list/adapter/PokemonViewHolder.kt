package com.example.pokedex.ui.pokemon_list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ItemPokemonsRvBinding
import com.example.pokedex.domain.model.Result

class PokemonViewHolder(
    private val binding: ItemPokemonsRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: Result) {
        binding.pokemon = pokemon
    }
}