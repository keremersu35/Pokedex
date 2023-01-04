package com.example.pokedex.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.domain.model.Result
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 20

internal class Pokemons(
    private val pokemonApi: PokemonApi
) : PokemonsRemoteDataSource {

    override fun getPokemons(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RemotePagingSource(service = pokemonApi)
            }
        ).flow
    }
}