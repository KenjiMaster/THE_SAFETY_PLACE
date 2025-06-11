package com.katespe.laprimera.primeraapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService : Service(), AudioManager.OnAudioFocusChangeListener {
    private val binder = MusicBinder()
    private var mp: MediaPlayer? = null
    private val songs = arrayOf("cancion1", "cancion2", "cancion3", "cancion4", "cancion5")
    private var currentSongIndex = -1
    private var isPlaying = false
    private val audioManager by lazy {
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    fun playSong(songName: String) {
        if (requestAudioFocus()) {
            val resourceId = resources.getIdentifier(songName, "raw", packageName)

            if (mp != null) {
                mp?.stop()
                mp?.release()
                mp = null
            }

            mp = MediaPlayer.create(this, resourceId)
            mp?.start()
            isPlaying = true
            currentSongIndex = songs.indexOf(songName)
        }
    }

    fun stopSong() {
        if (mp != null) {
            mp?.stop()
            mp?.release()
            mp = null
            isPlaying = false
        }
    }

    fun pauseSong() {
        if (mp != null && mp?.isPlaying == true) {
            mp?.pause()
            isPlaying = false
        }
    }

    fun startSong() {
        if (mp != null && !mp?.isPlaying!! == true) {
            mp?.start()
            isPlaying = true
        }
    }

    fun playNextSong() {
        if (currentSongIndex < songs.size - 1) {
            stopSong()
            currentSongIndex++
            val nextSong = songs[currentSongIndex]
            playSong(nextSong)
        }
    }

    fun playPreviousSong() {
        if (currentSongIndex > 0) {
            stopSong()
            currentSongIndex--
            val previousSong = songs[currentSongIndex]
            playSong(previousSong)
        }
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun requestAudioFocus(): Boolean {
        val result = audioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            stopSong()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSong()
    }
}




