package com.katespe.laprimera.primeraapp

import BluetoothConnection
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.katespe.laprimera.R

class MixerActivity : AppCompatActivity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    var aroma = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mixer)

        val linear1 = findViewById<LinearLayout>(R.id.linear1)
        val linear2 = findViewById<LinearLayout>(R.id.linear2)
        val linear3 = findViewById<LinearLayout>(R.id.linear3)

        val switch1 = findViewById<Switch>(R.id.switch2)

        val home = findViewById<ImageView>(R.id.imgs1)
        val ho = findViewById<ImageView>(R.id.imgs3)

        val sharedPreferences = getSharedPreferences("SwitchState", MODE_PRIVATE)
        val selectedFlavor = sharedPreferences.getString("selectedFlavor", "")

        val miView = findViewById<View>(R.id.img2)
        val imagen = findViewById<ImageView>(R.id.lev)
        val tx2 = findViewById<TextView>(R.id.tx2)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            // El dispositivo no admite Bluetooth
            // Maneja esta situación según tus necesidades
        }

        val bluetoothConnection = BluetoothConnection
        val mHandler = Handler()
        val mRunnable = Runnable {
            val mensaje = String.format("%d", aroma)
            BluetoothConnection.enviarValorBluetooth(mensaje)
        }



        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                aroma = 3111
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
                // El Switch está activado
                // Realiza la acción que desees cuando el Switch está activado
                // Por ejemplo, habilitar una funcionalidad
            } else {
                aroma = 611
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
                // El Switch está desactivado
                // Realiza la acción que desees cuando el Switch está desactivado
                // Por ejemplo, deshabilitar una funcionalidad
            }


        }

        if (selectedFlavor == "menta") {
            miView.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.verde))
            imagen.setImageResource(R.drawable.baseline_grass_48)
            tx2.text = "Menta"

        } else if (selectedFlavor == "lavanda") {
            imagen.setImageResource(R.drawable.baseline_bubble_chart_24)
            miView.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.morado))
            tx2.text = "Lavanda"
        }

        home.setOnClickListener {
            val intent = Intent(this, FistAppActivity::class.java)
            startActivity(intent)
        }
        ho.setOnClickListener {
            val intent = Intent(this, MasActivity::class.java)
            startActivity(intent)
        }

        linear1.setOnClickListener {
            val intent = Intent(this, ColorActivity::class.java)
            startActivity(intent)
        }
        linear2.setOnClickListener {
            val intent = Intent(this, SongListActivity::class.java)
            startActivity(intent)
        }
        linear3.setOnClickListener {
            val intent = Intent(this, ArmonicActivity::class.java)
            startActivity(intent)
        }
    }
    private fun saveSwitchState(switchId: String, isChecked: Boolean) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SwitchState", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(switchId, isChecked)
        editor.apply()
    }
}