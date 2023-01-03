package com.example.pokedex.data.source

import androidx.paging.PagingSource
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.utils.Response
import javax.inject.Inject

/*
class RemoteDataSource @Inject constructor(private val api: PokemonApi) : PagingSource<Int, Response<>>() {
    override suspend fun getTopAnimeCharacters(): NetworkResponseState<TopAnimeCharacterResponse> {
        return try {
            val response = api.getTopCharacters()
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }

    override suspend fun getSingleCharacter(id: String): NetworkResponseState<SingleCharacterResponse> {
        return try {
            val response = api.getSingleCharacterFull(id)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }
}*/
