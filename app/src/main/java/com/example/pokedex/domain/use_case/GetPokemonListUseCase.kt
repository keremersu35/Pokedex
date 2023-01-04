package com.example.pokedex.domain.use_case

import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
){
    operator fun invoke(limit: Int, offset: Int): Flow<Resource<Pokemon>> = flow{
        try {
            emit(Resource.Loading<Pokemon>())
            var pokemons = repository.getPokemonList(offset)
            emit(Resource.Success<Pokemon>(data = pokemons.data!!))
        }catch (e: HttpException){
            emit(Resource.Error<Pokemon>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){
            emit(Resource.Error<Pokemon>("Couldn't reach server."))
        }
    }
}