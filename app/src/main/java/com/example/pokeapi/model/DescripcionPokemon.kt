package com.example.pokeapi.model

data class DescripcionPokemon(

    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Double,
    val weight: Double,
    val stats: List<Stat>,
    val types: List<Type>,
)