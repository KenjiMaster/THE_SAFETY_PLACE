package com.katespe.laprimera.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.katespe.laprimera.primeraapp.adapter.EmotionAdapter
import com.katespe.laprimera.databinding.ActivityRecicleBinding

    class RecicleActivity : AppCompatActivity(), EmotionAdapter.EmotionItemClickListener {

    private lateinit var binding: ActivityRecicleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecicleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initReciclerView()
    }

        override fun onItemClick(position: Int) {
            val selectedEmotionNumber = position
            val intent = Intent(this, SolveActivity::class.java)
            intent.putExtra("emotion_number", selectedEmotionNumber)
            startActivity(intent)
        }

        private fun initReciclerView() {
            val manager = LinearLayoutManager(this)
            val decoration = DividerItemDecoration(this, manager.orientation)
            binding.emotions.layoutManager = manager
            val adapter = EmotionAdapter(this, ContentEmotions.emotionList)
            adapter.itemClickListener = this
            binding.emotions.adapter = adapter
        }
}