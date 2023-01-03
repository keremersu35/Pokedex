package com.example.pokedex.domain.use_case

import com.example.pokedex.domain.model.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
){
    operator fun invoke(id: Int): Flow<Response<PokemonDetail>> = flow{
        try {
            emit(Response.Loading<PokemonDetail>())
            var pokemon = repository.getPokemonDetail(id)
            emit(Response.Success<PokemonDetail>(pokemon.data!!))
        }catch (e: HttpException){
            emit(Response.Error<PokemonDetail>(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e: IOException){
            emit(Response.Error<PokemonDetail>("Couldn't reach server."))
        }
    }
}