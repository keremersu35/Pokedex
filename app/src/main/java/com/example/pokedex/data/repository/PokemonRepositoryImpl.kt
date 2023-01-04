package com.example.pokedex.data.repository

import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
): PokemonRepository() {

    override suspend fun getPokemonList(offset: Int): Resource<Pokemon> {
        return safeApiCall { api.getPokemonList(offset = offset) }
    }

    override suspend fun getPokemonDetail(id: Int): Resource<PokemonDetail> {
        return safeApiCall { api.getPokemonDetail(id) }
    }
}