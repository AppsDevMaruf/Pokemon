package com.maruf.pokemon.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.maruf.pokemon.R


class CustomAlertDialog(
    context: Context,
    private val message: String,
    private val animationResId: Int
) : Dialog(context), View.OnClickListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_layout)
        
        val messageTextView = findViewById<TextView>(R.id.text_view_message)
        messageTextView.text = message
        
        val animationView = findViewById<LottieAnimationView>(R.id.animation_view)
        animationView.setAnimation(animationResId)
        animationView.loop(true)
        animationView.playAnimation()
        
        val okButton = findViewById<Button>(R.id.button_ok)
        okButton.setOnClickListener(this)
    }
    
    override fun onClick(v: View) {
        dismiss()
    }
}