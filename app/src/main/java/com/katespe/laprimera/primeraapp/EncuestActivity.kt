package com.katespe.laprimera.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.adapter.emotionAdvanseAdapter
import com.katespe.laprimera.primeraapp.ModelEmotions

class EncuestActivity : AppCompatActivity(), emotionAdvanseAdapter.EmotionItemClickListener {

    private lateinit var adapter: emotionAdvanseAdapter
    private var selectedEmotionPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encuest)

        val recyclerView: RecyclerView = findViewById(R.id.emotions)
        val btnChangeActivity: Button = findViewById(R.id.btnChangeActivity)

        initRecyclerView(recyclerView)

        btnChangeActivity.setOnClickListener {
            if (selectedEmotionPosition != -1) {
                val intent = Intent(this, SolveActivity::class.java)
                intent.putExtra("emotion_number", selectedEmotionPosition)
                startActivity(intent)
            } else {
                // Maneja el caso en el que no se ha seleccionado ninguna SeekBar
                // Puedes mostrar un mensaje de error o tomar otra acción
            }
        }
    }

    override fun onItemClick(position: Int) {
        // Actualiza la posición de la SeekBar con el progreso más grande
        selectedEmotionPosition = position
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        recyclerView.layoutManager = manager
        val emotionsList = ContentEmotions.emotionList // Asegúrate de tener tu lista de emociones aquí
        adapter = emotionAdvanseAdapter(this, emotionsList)
        adapter.setOnEmotionItemClickListener(this) // Establece el listener
        recyclerView.adapter = adapter
    }
}











