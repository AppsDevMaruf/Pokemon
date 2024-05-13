package com.maruf.pokemon.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maruf.pokemon.databinding.ImageListItemBinding
import com.maruf.pokemon.network.Pokemon
import com.maruf.pokemon.ui.MainActivity
import com.maruf.pokemon.utils.DiffCallback

class PokemonImageAdapter : RecyclerView.Adapter<PokemonImageAdapter.ImageViewHolder>() {
    private var pokemonList = emptyList<Pokemon>()
    private var selectedItemPosition = RecyclerView.NO_POSITION
    fun updateList(list: List<Pokemon>) {
        val diffCallback = DiffCallback(pokemonList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        pokemonList = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(pokemonList[position], position == selectedItemPosition)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun setSelectedItemPosition(position: Int) {
        val previousPosition = selectedItemPosition
        selectedItemPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedItemPosition)
    }

    class ImageViewHolder(val binding: ImageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon, isSelected: Boolean) {
            binding.character = pokemon
            binding.listItemImage.strokeWidth = if (isSelected) 4F else 0F // Change stroke width based on selection
            binding.executePendingBindings()
        }

    }


}