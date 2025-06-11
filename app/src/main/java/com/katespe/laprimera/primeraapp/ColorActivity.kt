package com.katespe.laprimera.primeraapp

import BluetoothConnection
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.widget.AppCompatButton
import com.katespe.laprimera.R
import kotlin.math.max
import kotlin.math.min

class ColorActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private var originalColor = Color.rgb(255, 255, 255) // Color original, ajusta según tus necesidades
    private var transformedColor = originalColor // Color transformado, inicia con el color original
    private val seekBarMax = 200
    private var isColorChanged = false // Indica si el color ha cambiado
    private var isSeekBarEnabled = false // Indica si la barra de búsqueda debe estar habilitada
    private var isSeekBarRestored = false // Indica si la barra de búsqueda se ha restablecido
    private lateinit var campo1: EditText
    private lateinit var campo2: EditText
    private lateinit var campo3: EditText
    private lateinit var aplicador: AppCompatButton

    private lateinit var bluetoothAdapter: BluetoothAdapter

    private val BLUETOOTH_PERMISSION_REQUEST_CODE = 123 // Puedes elegir cualquier valor entero que desees
    var r = 0
    var g = 0
    var b = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        val image_View = findViewById<ImageView>(R.id.image_view)
        val aplicador = findViewById<AppCompatButton>(R.id.aplicador)
        val campo1 = findViewById<EditText>(R.id.txOut1)
        val campo2 = findViewById<EditText>(R.id.txOut2)
        val campo3 = findViewById<EditText>(R.id.txOut3)
        val salir = findViewById<Button>(R.id.botsalir)

        val switch = findViewById<Switch>(R.id.switch1)

        val textoIngresado1 = campo1.text.toString()
        val textoIngresado2 = campo2.text.toString()
        val textoIngresado3 = campo3.text.toString()



        salir.setOnClickListener{
            val intent = Intent(this, MixerActivity::class.java)
            startActivity(intent)
        }


        campo1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    actualizarBotonColor()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        campo2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    actualizarBotonColor()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })

        campo3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    actualizarBotonColor()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })

        image_View.isDrawingCacheEnabled = true
        image_View.buildDrawingCache(true)

        image_View.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE) {
                bitmap = image_View.drawingCache
                val x = motionEvent.x.toInt()
                val y = motionEvent.y.toInt()

                if (x >= 0 && x < bitmap.width && y >= 0 && y < bitmap.height) {
                    val pixel = bitmap.getPixel(x, y)

                    r = Color.red(pixel)
                    g = Color.green(pixel)
                    b = Color.blue(pixel)

                    transformedColor = Color.rgb(r, g, b)
                    aplicador.backgroundTintList = ColorStateList.valueOf(transformedColor)

                    campo1.hint = "Rojo:     $r"
                    campo2.hint = "Verde:   $g"
                    campo3.hint = "Azul:     $b"
                    isColorChanged = true
                    isSeekBarEnabled = true
                    isSeekBarRestored = false
                }
            }
            true
        }

        val lightIntensitySeekBar = findViewById<SeekBar>(R.id.light_intensity_seekbar)

        lightIntensitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val scaledProgress = progress.toDouble() / seekBarMax
                    val newBrightness = 0.5 + scaledProgress

                    r = (Color.red(transformedColor) * newBrightness).toInt()
                    g = (Color.green(transformedColor) * newBrightness).toInt()
                    b = (Color.blue(transformedColor) * newBrightness).toInt()

                    r = max(0, min(r, 255))
                    g = max(0, min(g, 255))
                    b = max(0, min(b, 255))

                    aplicador.backgroundTintList = ColorStateList.valueOf(Color.rgb(r, g, b))
                    campo1.hint = "Rojo:     $r"
                    campo2.hint = "Verde:   $g"
                    campo3.hint = "Azul:     $b"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (isColorChanged) {
                    isSeekBarRestored = true
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (isColorChanged) {
                    isColorChanged = false
                } else if (!isSeekBarRestored) {
                    seekBar?.progress = seekBarMax / 2
                }
            }
        })

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            // El dispositivo no admite Bluetooth
            // Maneja esta situación según tus necesidades
        }

        val bluetoothConnection = BluetoothConnection
        val mHandler = Handler()
        val mRunnable = Runnable {
            val mensaje = String.format("%03d%03d%03d", r, g, b)
            BluetoothConnection.enviarValorBluetooth(mensaje)
        }

        aplicador.setOnClickListener {
            // Si tienes permisos de Bluetooth, intenta realizar la conexión Bluetooth.
            BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
            mHandler.removeCallbacks(mRunnable)
            mHandler.postDelayed(mRunnable, 10)
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // El Switch está encendido
                // Realiza acciones relacionadas con el Switch encendido
            } else {
                // El Switch está apagado
                aplicador.isEnabled = bluetoothAdapter.isEnabled
                if (bluetoothAdapter.isEnabled) {
                    BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                    mHandler.removeCallbacks(mRunnable)
                    mHandler.postDelayed(mRunnable, 10)
                }
                // Realiza acciones relacionadas con el Switch apagado
            }
        }

        switch.isChecked = true


    }

    // Esta función se encuentra fuera del onCreate
    fun actualizarBotonColor() {
        val r = campo1.text.toString().toIntOrNull() ?: 0
        val g = campo2.text.toString().toIntOrNull() ?: 0
        val b = campo3.text.toString().toIntOrNull() ?: 0

        // Verifica si los valores están en el rango válido (0-255) y actualiza el color del botón.
        if (r in 0..255 && g in 0..255 && b in 0..255) {
            aplicador.backgroundTintList = ColorStateList.valueOf(Color.rgb(r, g, b))
        }
    }


}






