package com.example.pokedex.domain.repository

import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.utils.Response

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Response<Pokemon>
    suspend fun getPokemonDetail(id: Int): Response<PokemonDetail>
}