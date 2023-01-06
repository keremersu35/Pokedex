package com.example.pokedex.domain.model

data class Pokemon(
    val count: Long,
    val next: String,
    val previous: Any?,
    val results: List<Result>,
)

data class Result(
    val name: String,
    val url: String,
){
    fun getPokemonId(): Int{
        return url.split("/".toRegex()).dropLast(1).last().toInt()
    }
    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${getPokemonId()}.png"
    }
}


