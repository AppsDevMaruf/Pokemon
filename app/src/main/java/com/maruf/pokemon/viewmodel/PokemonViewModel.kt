package com.maruf.pokemon.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruf.pokemon.network.Pokemon
import com.maruf.pokemon.network.DataState
import com.maruf.pokemon.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(@ApplicationContext private val appContext: Context, private val repository: Repository) : ViewModel() {
    
    private val _pokemonState = MutableStateFlow<DataState<List<Pokemon>>>(DataState.Loading())
    val pokemonState: StateFlow<DataState<List<Pokemon>>> = _pokemonState.asStateFlow()
    
    fun fetchPokemonList() {
        viewModelScope.launch {
            val pokemonList = mutableListOf<Pokemon>()
            for (i in 1..151) {
                val pokemonDataState = repository.remote.getPokemon(i)

                pokemonDataState.collect { state ->

                    when (state) {
                        is DataState.Error -> {}
                        is DataState.Loading -> {}
                        is DataState.Success -> state.data?.let { pokemonList.add(it) }
                    }

                }

            }
            // Update state with the entire Pokemon list
            _pokemonState.value = DataState.Success(pokemonList)
        }
    }

}

