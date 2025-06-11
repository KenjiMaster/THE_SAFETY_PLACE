package com.katespe.laprimera.primeraapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.katespe.laprimera.R

class MasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mas)

        val home = findViewById<ImageView>(R.id.imgs1)
        val hom = findViewById<ImageView>(R.id.imgs2)

        val linear1 = findViewById<View>(R.id.mas1)
        val linear2 = findViewById<View>(R.id.mas2)
        val linear3 = findViewById<View>(R.id.mas3)

        home.setOnClickListener {
            val intent = Intent(this, FistAppActivity::class.java)
            startActivity(intent)
        }
        hom.setOnClickListener {
            val intent = Intent(this, MixerActivity::class.java)
            startActivity(intent)
        }
        linear1.setOnClickListener {
            val intent = Intent(this, ManejoActivity::class.java)
            startActivity(intent)
        }

        linear2.setOnClickListener {
            val intent = Intent(this, ApoyoActivity::class.java)
            startActivity(intent)
        }

        linear3.setOnClickListener {
            val intent = Intent(this, PregunActivity::class.java)
            startActivity(intent)
        }
    }
    }
