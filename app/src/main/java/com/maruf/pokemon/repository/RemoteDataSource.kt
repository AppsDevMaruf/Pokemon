package com.maruf.pokemon.repository

import com.maruf.pokemon.network.DataState
import com.maruf.pokemon.network.Pokemon
import com.maruf.pokemon.network.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val pokemonService: PokemonService) {

    suspend fun getPokemon(id: Int): Flow<DataState<Pokemon>> = flow {
        val response = try {
            pokemonService.getPokemon(id)
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown error"))
            throw e
        }
        if (response.isSuccessful) {
            emit(DataState.Success(response.body()))
        }


    }
}