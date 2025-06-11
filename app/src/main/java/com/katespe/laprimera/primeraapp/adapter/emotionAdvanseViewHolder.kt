package com.katespe.laprimera.primeraapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.ModelEmotions

class emotionAdvanseViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    private val tvEmotionName: TextView = view.findViewById(R.id.tvEmotionName)
    private val ivEmotion: ImageView = view.findViewById(R.id.ivEmotion)

    val seekBar: SeekBar = itemView.findViewById(R.id.seekBarEmotion)

    init {
        // Configura lo que desees hacer al inicializar el ViewHolder
        itemView.setOnClickListener {
            // Maneja los clics en los elementos del RecyclerView aquí
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Realiza la acción deseada al hacer clic en un elemento
            }
        }
    }
    fun bind(emotionMod: ModelEmotions) {
        // Actualiza las vistas con los datos del modelo
        tvEmotionName.text = emotionMod.nameEmotion
        Glide.with(ivEmotion.context).load(emotionMod.emoji).into(ivEmotion)
    }
}