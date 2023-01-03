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
)
