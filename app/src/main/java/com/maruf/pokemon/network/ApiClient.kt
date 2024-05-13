package com.maruf.pokemon.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
     val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val pokemonService: PokemonService by lazy {
        retrofit.create(PokemonService::class.java)
    }
    
    
}