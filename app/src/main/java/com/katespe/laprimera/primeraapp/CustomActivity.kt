package com.katespe.laprimera.primeraapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import com.katespe.laprimera.R

class CustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        val btnRep = findViewById<Button>(R.id.repe)
        val btnLuz = findViewById<Button>(R.id.luz)
        val btnMix = findViewById<Button>(R.id.mix)

        btnRep.setOnClickListener{
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }
        btnLuz.setOnClickListener{
            val intent = Intent(this, ColorActivity::class.java)
            startActivity(intent)
        }
        btnMix.setOnClickListener{
            val intent = Intent(this, MixerActivity::class.java)
            startActivity(intent)
        }

    }
}