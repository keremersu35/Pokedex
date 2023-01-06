package com.example.pokedex.ui.pokemon_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemPokemonsRvBinding
import com.example.pokedex.domain.model.Result


class PokemonListAdapter(private val pokemonList: List<Result>) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    private lateinit var binding: ItemPokemonsRvBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        binding = ItemPokemonsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        binding.pokemonImage.load(pokemon.getImageUrl()){
            crossfade(true)
            placeholder(R.drawable.loading)
            transformations(CircleCropTransformation())
        }
    }
}
