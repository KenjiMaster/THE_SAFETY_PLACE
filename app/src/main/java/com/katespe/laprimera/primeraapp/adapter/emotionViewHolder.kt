package com.katespe.laprimera.primeraapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.ModelEmotions

class EmotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Define tus vistas aquí
    var itemClickListener: EmotionAdapter.EmotionItemClickListener? = null
    private val tvEmotionName: TextView = view.findViewById(R.id.tvEmotionName)
    private val ivEmotion: ImageView = view.findViewById(R.id.ivEmotion)

    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener?.onItemClick(position)
            }
        }
    }


    fun bind(emotionMod: ModelEmotions) {
        // Actualiza las vistas con los datos del modelo
        tvEmotionName.text = emotionMod.nameEmotion
        Glide.with(ivEmotion.context).load(emotionMod.emoji).into(ivEmotion)
    }
}

