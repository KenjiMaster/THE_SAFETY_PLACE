package com.katespe.laprimera.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.katespe.laprimera.R

class ApoyoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apoyo)

        val home = findViewById<ImageView>(R.id.imgs1)
        val hom = findViewById<ImageView>(R.id.imgs2)
        val ho = findViewById<ImageView>(R.id.imgs3)

        home.setOnClickListener {
            val intent = Intent(this, FistAppActivity::class.java)
            startActivity(intent)
        }
        hom.setOnClickListener {
            val intent = Intent(this, MixerActivity::class.java)
            startActivity(intent)
        }
        ho.setOnClickListener {
            val intent = Intent(this, MasActivity::class.java)
            startActivity(intent)
        }
    }
}