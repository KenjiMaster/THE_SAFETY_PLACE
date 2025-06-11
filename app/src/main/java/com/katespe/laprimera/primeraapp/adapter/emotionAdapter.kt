package com.katespe.laprimera.primeraapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.ModelEmotions

class EmotionAdapter(private val context: Context, private val emotionList: List<ModelEmotions>) : RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>() {
    var itemClickListener: EmotionItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_emotions, parent, false)
        return EmotionViewHolder(view)


    }

    override fun getItemCount(): Int = emotionList.size



    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        val emotion = emotionList[position]
        holder.bind(emotion)
    }


    inner class EmotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define tus vistas aquí
        private val tvEmotionName: TextView = itemView.findViewById(R.id.tvEmotionName)
        private val ivEmotion: ImageView = itemView.findViewById(R.id.ivEmotion)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position) // Usa itemClickListener del adaptador
                }
            }
        }


        fun bind(emotionMod: ModelEmotions) {
            // Actualiza las vistas con los datos del modelo
            tvEmotionName.text = emotionMod.nameEmotion
            Glide.with(ivEmotion.context).load(emotionMod.emoji).into(ivEmotion)
        }
    }

    interface EmotionItemClickListener {
        fun onItemClick(position: Int)
    }

}


