package com.example.pokeapi.service

import com.example.pokeapi.model.DescripcionPokemon
import com.example.pokeapi.model.PokeType
import com.example.pokeapi.model.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    fun obtenerListaPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun obtenerPokemon(@Path("id") id: String): Call<DescripcionPokemon>

    @GET("type/{type}")
    fun obtenerType(@Path("type") id: String): Call<PokeType>
}