package com.katespe.laprimera.primeraapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.ModelEmotions

class emotionAdvanseAdapter(private val context: Context, private val emotionList: List<ModelEmotions>) : RecyclerView.Adapter<emotionAdvanseAdapter.EmotionAdvanseViewHolder>() {

    private var itemClickListener: EmotionItemClickListener? = null
    private var selectedEmotionPosition: Int = -1

    fun setOnEmotionItemClickListener(listener: EmotionItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotionAdvanseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_emotion_alternate, parent, false)
        return EmotionAdvanseViewHolder(view)
    }

    override fun getItemCount(): Int = emotionList.size

    override fun onBindViewHolder(holder: EmotionAdvanseViewHolder, position: Int) {
        val emotion = emotionList[position]
        holder.bind(emotion)

        holder.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // No necesitas hacer nada aquí si no quieres
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No necesitas hacer nada aquí si no quieres
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val clickedPosition = holder.adapterPosition
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    selectedEmotionPosition = clickedPosition
                    itemClickListener?.onItemClick(clickedPosition)
                }

            }
        })

        // Verifica si esta SeekBar tiene el progreso más grande
        if (position == selectedEmotionPosition) {
            // Personaliza la apariencia de la SeekBar con el progreso más grande, si lo deseas
        } else {
            // Restablece la apariencia predeterminada
        }
    }

    inner class EmotionAdvanseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seekBar: SeekBar = itemView.findViewById(R.id.seekBarEmotion)
        private val tvEmotionName: TextView = itemView.findViewById(R.id.tvEmotionName)
        private val ivEmotion: ImageView = itemView.findViewById(R.id.ivEmotion)

        fun bind(emotionMod: ModelEmotions) {
            tvEmotionName.text = emotionMod.nameEmotion
            Glide.with(ivEmotion.context).load(emotionMod.emoji).into(ivEmotion)
        }
    }

    interface EmotionItemClickListener {
        fun onItemClick(position: Int)
    }
}






