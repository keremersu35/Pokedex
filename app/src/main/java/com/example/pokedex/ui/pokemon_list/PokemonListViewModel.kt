package com.example.pokedex.ui.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pokedex.data.source.RemotePagingSource
import com.example.pokedex.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel(){

    val pokemonList = Pager(PagingConfig(1)) {
        RemotePagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}