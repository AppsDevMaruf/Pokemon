package com.maruf.pokemon.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.maruf.pokemon.R

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_error_placeholder)
        .error(R.drawable.ic_error_placeholder)
        .into(this)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setId")
fun TextView.setId(id: String?) {
        text = "#${id}"
}
