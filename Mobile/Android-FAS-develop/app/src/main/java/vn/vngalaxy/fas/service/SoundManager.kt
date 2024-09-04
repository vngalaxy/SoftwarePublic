package vn.vngalaxy.fas.service

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes

class SoundManager(
    private val context: Context,
) {
    private val audio by lazy {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private var mediaPlayer: MediaPlayer? = null

    private fun setMaxVolume(streamType: Int = AudioManager.STREAM_MUSIC) {
        runCatching {
            val maxVolume = audio.getStreamMaxVolume(streamType)
            val currentVolume = audio.getStreamVolume(streamType)
            if (currentVolume != maxVolume) {
                audio.setStreamVolume(streamType, maxVolume, 0)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun play(@RawRes resId: Int, isLooping: Boolean = false, onCompletion: (() -> Unit)? = null) = runCatching {
        mediaPlayer = MediaPlayer.create(context, resId)
        setMaxVolume()
        mediaPlayer?.setVolume(1f, 1f)
        mediaPlayer?.isLooping = isLooping
        mediaPlayer?.setOnCompletionListener {
            onCompletion?.invoke()
            stop()
        }
        mediaPlayer?.start()
        mediaPlayer
    }.onFailure {
        it.printStackTrace()
    }

    fun stop() = runCatching {
        Log.d("Dataaaaa", "stop Sound")
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}