package com.example.pokedex.data

import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.utils.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse<Pokemon>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") name: Int): PokemonResponse<PokemonDetail>
}