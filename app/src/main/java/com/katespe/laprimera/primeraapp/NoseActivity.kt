package com.katespe.laprimera.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.katespe.laprimera.R

class NoseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nose)

        val btnSalir = findViewById<Button>(R.id.botonSalir)
        val btnInit = findViewById<Button>(R.id.inicio)

        btnSalir.setOnClickListener{
            val intent = Intent(this, FistAppActivity::class.java)
            startActivity(intent)
        }
        btnInit.setOnClickListener{
            val intent = Intent(this, EncuestActivity::class.java)
            startActivity(intent)
        }
    }
}