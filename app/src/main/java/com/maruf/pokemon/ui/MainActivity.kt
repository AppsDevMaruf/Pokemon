package com.maruf.pokemon.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.maruf.pokemon.databinding.ActivityMainBinding
import com.maruf.pokemon.network.DataState
import com.maruf.pokemon.network.Pokemon
import com.maruf.pokemon.ui.adapter.PokemonImageAdapter
import com.maruf.pokemon.utils.Constants
import com.maruf.pokemon.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pokemonImageAdapter by lazy { PokemonImageAdapter() }
    private val viewModel: PokemonViewModel by viewModels()

    companion object{
        const val FIRST_INDEX = 0
        const val SECOND_INDEX = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchData()

    }

    private fun fetchData() {
        viewModel.pokemonState.onEach { state ->
            binding.progressBar.visibility = View.GONE
            when (state) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    val pokemonList = state.data
                    setUiData(pokemonList)
                }

                is DataState.Error -> {
                    // Show error message
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)
        viewModel.fetchPokemonList()
    }

    @SuppressLint("SetTextI18n")
    private fun setUiData(pokemonList: List<Pokemon>?) {
        pokemonList?.let { pokemonImageAdapter.updateList(it) }
        binding.carouselRecyclerview.adapter = pokemonImageAdapter
        val pos = binding.carouselRecyclerview.getSelectedPosition()
        setPokemonDetails(pokemonList, pos)
        pokemonImageAdapter.setSelectedItemPosition(pos)
        binding.carouselRecyclerview.apply {
            setAlpha(true)
            setInfinite(true)
        }
        binding.carouselRecyclerview.setItemSelectListener(object :
            CarouselLayoutManager.OnSelected {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(position: Int) {
                setPokemonDetails(pokemonList, position)
                pokemonImageAdapter.setSelectedItemPosition(position)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setPokemonDetails(pokemonList: List<Pokemon>?, position: Int) {
        binding.name.text = pokemonList?.get(position)?.name ?: Constants.UNKNOWN
        "Height: ${pokemonList?.get(position)?.height?.toString() ?: Constants.UNKNOWN}".also { binding.height.text = it }
        binding.weight.text =
            "Weight: ${pokemonList?.get(position)?.weight?.toString() ?: Constants.UNKNOWN}"
        binding.type.text = pokemonList?.get(position)?.types?.get(FIRST_INDEX)?.type?.name?.let { firstType ->
            pokemonList[position].types?.getOrNull(SECOND_INDEX)?.type?.name?.let { secondType ->
                "Type: $firstType/$secondType"
            } ?: "Type: $firstType"
        } ?:Constants.UNKNOWN

    }


}