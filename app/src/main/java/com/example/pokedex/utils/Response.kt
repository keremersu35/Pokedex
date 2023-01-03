package com.example.pokedex.utils

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Response<T>(data = data)
    class Error<T>(errorMessage: String) : Response<T>(message = errorMessage)
    class Loading<T> : Response<T>()
}