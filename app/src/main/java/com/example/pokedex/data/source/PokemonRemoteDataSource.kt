package com.example.pokedex.data.source

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.pokedex.domain.model.Result

interface PokemonsRemoteDataSource {

    fun getPokemons(): Flow<PagingData<Result>>
}