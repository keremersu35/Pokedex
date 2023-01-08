package com.example.pokedex.ui.pokemon_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.pokedex.databinding.ItemPokemonsRvBinding
import com.example.pokedex.domain.model.Result

class PokemonListAdapter :
    PagingDataAdapter<Result, PokemonViewHolder>(differCallback) {

    private lateinit var binding: ItemPokemonsRvBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        binding = ItemPokemonsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)!!
        holder.bind(pokemon)
    }
    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.getPokemonId() == oldItem.getPokemonId()
            }
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}
