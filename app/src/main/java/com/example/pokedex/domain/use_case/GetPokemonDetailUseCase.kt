package com.example.pokedex.domain.use_case

import com.example.pokedex.data.repository.PokemonRepositoryImpl
import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepositoryImpl
){
    operator fun invoke(id: Int): Flow<Resource<PokemonDetail>> = flow{
        try {
            emit(Resource.Loading<PokemonDetail>())
            var pokemon = repository.getPokemonDetail(id)
            emit(Resource.Success<PokemonDetail>(pokemon.data!!))
        }catch (e: HttpException){
            emit(Resource.Error<PokemonDetail>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){
            emit(Resource.Error<PokemonDetail>("Couldn't reach server."))
        }
    }
}