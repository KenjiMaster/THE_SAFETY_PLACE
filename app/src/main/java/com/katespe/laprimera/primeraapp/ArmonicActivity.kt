package com.katespe.laprimera.primeraapp

import BluetoothConnection
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import com.katespe.laprimera.R
import android.content.SharedPreferences
import android.os.Handler


class ArmonicActivity : AppCompatActivity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    var aroma = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_armonic)

        val salir = findViewById<Button>(R.id.botsalir)
        val switch1 = findViewById<Switch>(R.id.switch1)
        val switch2 = findViewById<Switch>(R.id.switch2)
        val switch3 = findViewById<Switch>(R.id.switch3)

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

        val sharedPreferences: SharedPreferences = getSharedPreferences("SwitchState", MODE_PRIVATE)
        switch1.isChecked = sharedPreferences.getBoolean("switch1", false)
        switch2.isChecked = sharedPreferences.getBoolean("switch2", false)
        switch3.isChecked = sharedPreferences.getBoolean("switch3", false)

        switch2.isEnabled = switch1.isChecked
        switch3.isEnabled = switch1.isChecked



        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            switch2.isEnabled = isChecked
            switch3.isEnabled = isChecked
            saveSwitchState("switch1", isChecked)
            if (!isChecked) {
                switch2.isChecked = false
                switch3.isChecked = false
            }
        }

        // Cuando "menta" se selecciona
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            saveSwitchState("switch2", isChecked)
            if (isChecked) {
                switch3.isChecked = false
                // Guarda la elección en las preferencias compartidas
                val sharedPreferences = getSharedPreferences("SwitchState", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("selectedFlavor", "menta")
                editor.apply()
                aroma = 1
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
            }else{
                aroma = 4
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
            }
        }

// Cuando "lavanda" se selecciona
        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            saveSwitchState("switch3", isChecked)
            if (isChecked) {
                switch2.isChecked = false
                // Guarda la elección en las preferencias compartidas
                val sharedPreferences = getSharedPreferences("SwitchState", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("selectedFlavor", "lavanda")
                editor.apply()
                aroma = 2
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
            }else{
                aroma = 5
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)
            }
        }





        salir.setOnClickListener{
            val intent = Intent(this, MixerActivity::class.java)
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