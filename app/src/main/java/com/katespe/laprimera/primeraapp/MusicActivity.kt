package com.katespe.laprimera.primeraapp

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.katespe.laprimera.R
import android.content.Intent
import android.widget.Toast

class MusicActivity : AppCompatActivity(), AudioManager.OnAudioFocusChangeListener {

    companion object {
        const val REQUEST_CODE = 1
    }

    private var mp: MediaPlayer? = null
    private lateinit var canciones: MutableList<String>
    private var cancionActualIndex = -1
    private var loopMode = false
    private var audioManager: AudioManager? = null

    private lateinit var controles: List<MaterialButton>
    private lateinit var nombreCancion: TextView
    private lateinit var caratula: ImageView
    private lateinit var progressBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        controles = listOf(
            findViewById(R.id.anterior),
            findViewById(R.id.stop),
            findViewById(R.id.play),
            findViewById(R.id.siguient)
        )

        nombreCancion = findViewById(R.id.txt)
        caratula = findViewById(R.id.caratula)
        progressBar = findViewById(R.id.progressBar)

        canciones = mutableListOf(
            "cancion1",
            "cancion2",
            "cancion3",
            "cancion4",
            "cancion5"
        )

        controles[2].setOnClickListener { playClicked(it) }
        controles[1].setOnClickListener { stopClicked(it) }
        controles[0].setOnClickListener { anteriorClicked(it) }
        controles[3].setOnClickListener { siguienteClicked(it) }

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && mp != null) {
                    val duration = mp!!.duration
                    val newPosition = (duration * progress) / 100
                    mp!!.seekTo(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val loopButton = findViewById<MaterialButton>(R.id.loopButton)
        loopButton.setOnClickListener {
            loopMode = !loopMode
            if (loopMode) {
                loopButton.setIconResource(R.drawable.baseline_repeat_48)
            } else {
                loopButton.setIconResource(R.drawable.baseline_repeat_48)
            }
        }

        val playlistButton = findViewById<MaterialButton>(R.id.playlistButton)
        playlistButton.setOnClickListener {
            val intent = Intent(this, SongListActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val action = data?.action
            if (action == "playSong") {
                val songName = data.getStringExtra("songName")
                if (songName != null) {
                    // Reproduce la canción con el nombre proporcionado (songName)
                    // Asegúrate de manejar la reproducción en MusicActivity
                }
            }
        }
    }


    private fun sendSongDataToSongListActivity(songName: String) {
        val intent = Intent()
        intent.putExtra("songName", songName)
        setResult(RESULT_OK, intent)
    }

    private fun loadAndPlayCurrentSong() {
        try {
            stopAndReleaseMediaPlayer()
            val currentSong = canciones[cancionActualIndex]
            val resourceId = resources.getIdentifier(currentSong, "raw", packageName)
            mp = MediaPlayer.create(this, resourceId)

            val result = audioManager?.requestAudioFocus(
                this,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp?.isLooping = loopMode
                controles[2].setIconResource(R.drawable.baseline_pause_48)
                nombreCancion.visibility = View.VISIBLE
                nombreCancion.text = currentSong
                mp?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopAndReleaseMediaPlayer() {
        mp?.stop()
        mp?.release()
        mp = null
        audioManager?.abandonAudioFocus(this)
        sendSongDataToSongListActivity("")
    }

    fun playClicked(v: View) {
        try {
            if (mp == null) {
                loadAndPlayCurrentSong()
            } else {
                if (!mp!!.isPlaying) {
                    mp!!.start()
                    controles[2].setIconResource(R.drawable.baseline_pause_48)
                    nombreCancion.visibility = View.VISIBLE
                } else {
                    mp!!.pause()
                    controles[2].setIconResource(R.drawable.baseline_play_arrow_48)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopClicked(v: View) {
        try {
            if (mp != null) {
                stopAndReleaseMediaPlayer()
                controles[2].setIconResource(R.drawable.baseline_play_arrow_48)
                nombreCancion.visibility = View.INVISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun anteriorClicked(v: View) {
        try {
            if (cancionActualIndex > 0) {
                stopAndReleaseMediaPlayer()
                cancionActualIndex--
                loadAndPlayCurrentSong()
                sendSongDataToSongListActivity(canciones[cancionActualIndex])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun siguienteClicked(v: View) {
        try {
            if (cancionActualIndex < canciones.size - 1) {
                stopAndReleaseMediaPlayer()
                cancionActualIndex++
                loadAndPlayCurrentSong()
                sendSongDataToSongListActivity(canciones[cancionActualIndex])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            stopAndReleaseMediaPlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAndReleaseMediaPlayer()
    }
}


















