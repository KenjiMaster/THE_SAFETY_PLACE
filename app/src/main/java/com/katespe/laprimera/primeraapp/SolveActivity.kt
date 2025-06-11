package com.katespe.laprimera.primeraapp

import BluetoothConnection
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.katespe.laprimera.R


class SolveActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private lateinit var canciones: MutableList<String>
    private var cancionActualIndex = -1
    private var loopMode = false
    private var audioManager: AudioManager? = null

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var mediaPlayer: MediaPlayer? = null


    var r = 0
    var g = 0
    var b = 0

    var aroma = 0

    var nom = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve)

        mediaPlayer = MediaPlayer()

        // Carga la canción que deseas reproducir (asegúrate de que la canción esté en los recursos "raw")


        // Prepara el MediaPlayer


        // Inicia la reproducción



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
        val intent = intent
        val emotionNumber = intent.getIntExtra("emotion_number", -1)

        val lag1 = findViewById<ImageView>(R.id.lag1)
        val textView1 = findViewById<TextView>(R.id.texto1)
        val mas1 = findViewById<View>(R.id.mas1)

        val lag2 = findViewById<ImageView>(R.id.lag2)
        val textView2 = findViewById<TextView>(R.id.texto2)
        val mas2 = findViewById<View>(R.id.mas2)

        val lag3 = findViewById<ImageView>(R.id.lag3)
        val textView3 = findViewById<TextView>(R.id.texto3)
        val mas3 = findViewById<View>(R.id.mas3)


        lag1.visibility = View.INVISIBLE
        textView1.visibility = View.INVISIBLE

        lag2.visibility = View.INVISIBLE
        textView2.visibility = View.INVISIBLE

        lag3.visibility = View.INVISIBLE
        textView3.visibility = View.INVISIBLE

        val animDuration = 1000 // Duración de la animación en milisegundos
        if (emotionNumber == 0){
            r = 249
            g = 109  //amarillo
            b = 6
            aroma = 2
            var songResourceId = resources.getIdentifier("cancion1", "raw", packageName)
            mediaPlayer?.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + songResourceId))
            mediaPlayer?.prepare()

        }else if (emotionNumber == 1){
            r = 249
            g = 83      //Naranja
            b = 6
            aroma = 2
            var songResourceId = resources.getIdentifier("cancion2", "raw", packageName)
            mediaPlayer?.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + songResourceId))
            mediaPlayer?.prepare()
        }
        else if (emotionNumber == 2){
            r = 6
            g = 175    //Azul
            b = 249
            aroma = 1
            var songResourceId = resources.getIdentifier("cancion3", "raw", packageName)
            mediaPlayer?.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + songResourceId))
            mediaPlayer?.prepare()
        }else if (emotionNumber == 3){
            r = 20
            g = 31       //Morado
            b = 213
            aroma = 2
            var songResourceId = resources.getIdentifier("cancion4", "raw", packageName)
            mediaPlayer?.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + songResourceId))
            mediaPlayer?.prepare()
        }else if (emotionNumber == 4){
            r = 122
            g = 220       //Morado
            b = 25
            aroma = 2
            var songResourceId = resources.getIdentifier("cancion5", "raw", packageName)
            mediaPlayer?.setDataSource(this, Uri.parse("android.resource://" + packageName + "/" + songResourceId))
            mediaPlayer?.prepare()
        }


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

        // Función para animar la escritura del texto
        fun animateTextWithCompletion(textView: TextView, text: String, delay: Long, completion: () -> Unit) {
            textView.postDelayed({
                textView.visibility = View.VISIBLE
                animateText(textView, text, 0) {
                    completion()
                }
            }, delay)
        }

        // Animación de aparición para la ImageView 1 (retraso de 1 segundo)
        lag1.postDelayed({
            lag1.visibility = View.VISIBLE
            lag1.animate()
                .alpha(1f)
                .setDuration(animDuration.toLong())
            animateTextWithCompletion(textView1, "ILUMINACIÓN", 2000) {
                // Cuando se complete la animación de TextView 1
                // Cambiar el color de fondo de mas1 utilizando backgroundTint
                mas1.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                BluetoothConnection.connect(this, "98:D3:51:F9:3C:76")
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 10)

                // Animación de aparición para la ImageView 2 (retraso de 1 segundo)
                lag2.postDelayed({
                    lag2.visibility = View.VISIBLE
                    lag2.animate()
                        .alpha(1f)
                        .setDuration(animDuration.toLong())
                    animateTextWithCompletion(textView2, "AROMAS", 2000) {
                        // Cuando se complete la animación de TextView 2
                        // Cambiar el color de fondo de mas2 utilizando backgroundTint
                        mas2.backgroundTintList = ColorStateList.valueOf(Color.GREEN)

                        // Animación de aparición para la ImageView 3 (retraso de 1 segundo)
                        lag3.postDelayed({
                            lag3.visibility = View.VISIBLE
                            lag3.animate()
                                .alpha(1f)
                                .setDuration(animDuration.toLong())
                            animateTextWithCompletion(textView3, "SONIDOS", 2000) {
                                // Cuando se complete la animación de TextView 3
                                // Cambiar el color de fondo de mas3 utilizando backgroundTint
                                mas3.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                                mediaPlayer?.start()

                            }
                        }, 2000)
                    }
                }, 2000)
            }
        }, 1000)
    }

    private fun animateText(textView: TextView, text: String, index: Int, completion: () -> Unit) {
        if (index < text.length) {
            val newText = text.substring(0, index + 1)
            textView.text = newText
            textView.postDelayed({
                animateText(textView, text, index + 1, completion)
            }, 100) // Intervalo de escritura de caracteres en milisegundos
        } else {
            completion()
        }
    }

    override fun onPause() {
        super.onPause()

        // Pausa la reproducción de la música cuando la actividad pasa a segundo plano
        mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Libera recursos del MediaPlayer cuando la actividad se destruye
        mediaPlayer?.release()
    }



}








