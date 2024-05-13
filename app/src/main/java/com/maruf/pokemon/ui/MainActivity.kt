package com.maruf.pokemon.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.maruf.pokemon.R
import com.maruf.pokemon.databinding.ActivityMainBinding
import com.maruf.pokemon.network.DataState
import com.maruf.pokemon.network.Pokemon
import com.maruf.pokemon.ui.adapter.PokemonImageAdapter
import com.maruf.pokemon.utils.CustomAlertDialog
import com.maruf.pokemon.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val pAdapter by lazy { PokemonImageAdapter() }
    private val viewModel: PokemonViewModel by viewModels()

    private val dialog: CustomAlertDialog by lazy {
        CustomAlertDialog(this, "Please Wait...!", R.raw.loading_animation)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog.setCancelable(false)
        fetchData()

    }

    private fun fetchData() {
        viewModel.pokemonState.onEach { state ->

            when (state) {
                is DataState.Loading -> {
                    // binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    val pokemonList = state.data
                    setUiData(pokemonList)
                    if (pokemonList?.size != 151) {
                        dialog.show()
                        // binding.progressBar.visibility = View.VISIBLE
                    } else {
                        //  binding.progressBar.visibility = View.GONE
                        dialog.dismiss()
                    }

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
        pokemonList?.let { pAdapter.updateList(it) }
        binding.carouselRecyclerview.adapter = pAdapter
        val pos = binding.carouselRecyclerview.getSelectedPosition()
        setPokemonDetails(pokemonList, pos)
        binding.carouselRecyclerview.apply {
            setAlpha(true)
            setInfinite(true)
            setHasFixedSize(true)


        }




        binding.carouselRecyclerview.setItemSelectListener(object :
            CarouselLayoutManager.OnSelected {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(position: Int) {
                pAdapter.setMiddlePosition(position, binding.carouselRecyclerview)
                setPokemonDetails(pokemonList, position)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setPokemonDetails(pokemonList: List<Pokemon>?, position: Int) {
        binding.name.text = pokemonList?.get(position)?.name ?: "unknown"
        binding.height.text =
            "Height: ${pokemonList?.get(position)?.height?.toString() ?: "unknown"}"
        binding.weight.text =
            "Weight: ${pokemonList?.get(position)?.weight?.toString() ?: "unknown"}"
        binding.type.text = pokemonList?.get(position)?.types?.get(0)?.type?.name?.let { type1 ->
            pokemonList[position].types?.getOrNull(1)?.type?.name?.let { type2 ->
                "Type: $type1/$type2"
            } ?: "Type: $type1"
        } ?: "unknown"

    }


}