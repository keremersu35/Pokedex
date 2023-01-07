package com.example.pokedex.data.remote

import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET(Constants.POKEMON)
    suspend fun getPokemonList(
        @Query(Constants.OFFSET) offset: Int,
        ): Response<Pokemon>

    @GET(Constants.POKEMON_DETAIL)
    suspend fun getPokemonDetail(@Path(Constants.ID) id: Int): Response<PokemonDetail>
}