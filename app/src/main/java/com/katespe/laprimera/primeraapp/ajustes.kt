package com.katespe.laprimera.primeraapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.katespe.laprimera.R
import com.katespe.laprimera.primeraapp.FistAppActivity
import java.io.IOException
import java.util.*

class ajustes : AppCompatActivity() {
    lateinit var mBtAdapter: BluetoothAdapter
    var mAddressDevices: ArrayAdapter<String>? = null
    var mNameDevices: ArrayAdapter<String>? = null

    var idBtnOnBT: Button? = null
    var idBtnOffBT: Button? = null
    var idBtnConect: Button? = null
    var idBtnEnviar: Button? = null

    var idBtnDispBT: Button? = null
    var idSpinDisp: Spinner? = null
    var idTextOut: EditText? = null

    var salir: Button? = null

    companion object {
        val m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        mAddressDevices = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        mNameDevices = ArrayAdapter(this, android.R.layout.simple_spinner_item)

        idBtnOnBT = findViewById<Button>(R.id.btnOn)
        idBtnOffBT = findViewById<Button>(R.id.btnOff)
        idBtnConect = findViewById<Button>(R.id.btnConex)
        idBtnEnviar = findViewById<Button>(R.id.btnEnviar)

        idBtnDispBT = findViewById<Button>(R.id.btnDisp)
        idSpinDisp = findViewById<Spinner>(R.id.spinDisp)
        idTextOut = findViewById<EditText>(R.id.txOut)

        salir = findViewById<Button>(R.id.btnGrad)

        salir?.setOnClickListener {
            val intent = Intent(this, FistAppActivity::class.java)
            startActivity(intent)
        }

        //--------------------------------------------------
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 1)
        }

        //--------------------------------------------------
        val someActivityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == 1) {
                // La actividad de solicitud de permiso de Bluetooth ha sido registrada
                // Aquí puedes realizar cualquier acción adicional después de que el usuario conceda los permisos.
                Toast.makeText(this, "ACTIVIDAD REGISTRADA", Toast.LENGTH_SHORT).show()
            }
        }

        // Inicialización del bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter()

        // Checar si está encendido o apagado
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth no está disponible en este dispositivo", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Bluetooth está disponible en este dispositivo", Toast.LENGTH_LONG).show()
        }

        //--------------------------------------------------
        //--------------------------------------------------
        // Boton Encender bluetooth
        idBtnOnBT?.setOnClickListener {
            if (mBtAdapter.isEnabled) {
                // Si ya está activado
                Toast.makeText(this, "Bluetooth ya se encuentra activado", Toast.LENGTH_LONG).show()
            } else {
                // Encender Bluetooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Solicitando permisos de Bluetooth", Toast.LENGTH_SHORT).show()
                    someActivityResultLauncher.launch(enableBtIntent)
                }
            }
        }

        // Boton apagar bluetooth
        idBtnOffBT?.setOnClickListener {
            if (!mBtAdapter.isEnabled) {
                // Si ya está desactivado
                Toast.makeText(this, "Bluetooth ya se encuentra desactivado", Toast.LENGTH_LONG).show()
            } else {
                // Apagar Bluetooth
                mBtAdapter.disable()
                Toast.makeText(this, "Se ha desactivado el bluetooth", Toast.LENGTH_LONG).show()
            }
        }

        // Boton dispositivos emparejados
        idBtnDispBT?.setOnClickListener {
            if (mBtAdapter.isEnabled) {
                val pairedDevices: Set<BluetoothDevice>? = mBtAdapter.bondedDevices
                mAddressDevices?.clear()
                mNameDevices?.clear()

                pairedDevices?.forEach { device ->
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    mAddressDevices?.add(deviceHardwareAddress)
                    mNameDevices?.add(deviceName)
                }

                // Actualizo los dispositivos
                idSpinDisp?.adapter = mNameDevices
            } else {
                val noDevices = "Ningún dispositivo pudo ser emparejado"
                mAddressDevices?.add(noDevices)
                mNameDevices?.add(noDevices)
                Toast.makeText(this, "Primero vincule un dispositivo Bluetooth", Toast.LENGTH_LONG).show()
            }
        }

        idBtnConect?.setOnClickListener {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    val IntValSpin = idSpinDisp?.selectedItemPosition ?: -1
                    if (IntValSpin != -1) {
                        m_address = mAddressDevices?.getItem(IntValSpin).toString()
                        Toast.makeText(this, m_address, Toast.LENGTH_LONG).show()
                        // Cancelar descubrimiento porque ralentiza la conexión.
                        mBtAdapter.cancelDiscovery()
                        val device: BluetoothDevice = mBtAdapter.getRemoteDevice(m_address)
                        m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                        m_bluetoothSocket?.connect()
                    } else {
                        Toast.makeText(this, "Seleccione un dispositivo para conectarse", Toast.LENGTH_SHORT).show()
                    }
                }

                Toast.makeText(this, "CONEXIÓN EXITOSA", Toast.LENGTH_LONG).show()
                Log.i("MainActivity", "CONEXIÓN EXITOSA")
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "ERROR DE CONEXIÓN", Toast.LENGTH_LONG).show()
                Log.i("MainActivity", "ERROR DE CONEXIÓN")
            }
        }

        idBtnEnviar?.setOnClickListener {
            val mensaje_out: String = idTextOut?.text.toString()
            if (mensaje_out.isNotEmpty()) {
                sendCommand(mensaje_out)
            } else {
                Toast.makeText(this, "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket?.outputStream?.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}



