package com.example.pokedex.utils

sealed class PokemonResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : PokemonResponse<T>(data = data)
    class Error<T>(errorMessage: String) : PokemonResponse<T>(message = errorMessage)
    class Loading<T> : PokemonResponse<T>()
}