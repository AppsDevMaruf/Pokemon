package com.maruf.pokemon.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Response<Pokemon>
}