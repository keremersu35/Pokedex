package com.example.pokedex.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonDetail(
    val height: Long,
    val id: Long,
    val name: String,
    val weight: Long,
){
    fun getImage(): String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    }
}

