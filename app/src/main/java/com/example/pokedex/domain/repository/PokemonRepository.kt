package com.example.pokedex.domain.repository

import com.example.pokedex.base.BaseRepository
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.utils.Resource

abstract class PokemonRepository : BaseRepository(){
    abstract suspend fun getPokemonList(offset: Int): Resource<Pokemon>
    abstract suspend fun getPokemonDetail(id: Int): Resource<PokemonDetail>
}