package com.katespe.laprimera.primeraapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.katespe.laprimera.R

class SongListActivity : AppCompatActivity(), AudioManager.OnAudioFocusChangeListener {
    private var mp: MediaPlayer? = null
    private var currentSongIndex: Int = -1
    private lateinit var audioManager: AudioManager

    private val songs = arrayOf("cancion1", "cancion2", "cancion3", "cancion4", "cancion5")

    private lateinit var songListView: ListView
    private lateinit var songTitleTextView: TextView
    private lateinit var playPauseButton: ImageView
    private lateinit var nextButton: ImageView

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        songListView = findViewById(R.id.list)
        songTitleTextView = findViewById(R.id.txtSongName)
        playPauseButton = findViewById(R.id.btnPlayPause)
        nextButton = findViewById(R.id.btnNext)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songListView.adapter = adapter

        songListView.setOnItemClickListener { _, _, position, _ ->
            currentSongIndex = position
            playSelectedSong()
        }

        playPauseButton.setOnClickListener {
            togglePlayPause()
        }

        nextButton.setOnClickListener {
            playNextSong()
        }

        val songName = sharedPreferences.getString("currentSongName", null)
        if (!songName.isNullOrBlank()) {
            songTitleTextView.text = songName
            updatePlayPauseButtonText()
        }

        val bottomBar = findViewById<View>(R.id.bottomBar)
        bottomBar.setOnClickListener {
            val intent = Intent(this, MixerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playSelectedSong() {
        val result = audioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            val songName = songs[currentSongIndex]
            val resourceId = resources.getIdentifier(songName, "raw", packageName)

            if (mp != null) {
                mp?.stop()
                mp?.release()
            }

            mp = MediaPlayer.create(this, resourceId)
            songTitleTextView.text = songName
            mp?.start()
            updatePlayPauseButtonText()

            with(sharedPreferences.edit()) {
                putString("currentSongName", songName)
                apply()
            }
        }
    }

    private fun togglePlayPause() {
        if (mp != null) {
            if (mp!!.isPlaying) {
                mp?.pause()
            } else {
                mp?.start()
            }
            updatePlayPauseButtonText()
        }
    }

    private fun playNextSong() {
        if (currentSongIndex < songs.size - 1) {
            currentSongIndex++
        } else {
            currentSongIndex = 0
        }

        playSelectedSong()
    }

    private fun updatePlayPauseButtonText() {
        if (mp != null && mp!!.isPlaying) {
            playPauseButton.setImageResource(R.drawable.baseline_pause_48)
        } else {
            playPauseButton.setImageResource(R.drawable.baseline_play_arrow_48)
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (mp?.isPlaying == true) {
                    mp?.pause()
                    updatePlayPauseButtonText()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                if (mp?.isPlaying == true) {
                    mp?.pause()
                    updatePlayPauseButtonText()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mp != null) {
            mp?.stop()
            mp?.release()
        }

        audioManager.abandonAudioFocus(this)
    }
}
























