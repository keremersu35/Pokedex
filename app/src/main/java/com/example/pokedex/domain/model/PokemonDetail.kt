package com.example.pokedex.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PokemonDetail(
    val height: Long,
    val id: Long,
    val name: String,
    val weight: Long,
)

