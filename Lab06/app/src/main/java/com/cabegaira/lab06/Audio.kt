package com.cabegaira.lab06

import android.os.Bundle
import android.media.MediaPlayer
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.audio_activity.*
class Audio : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_activity)

        mediaPlayer=MediaPlayer.create(this, R.raw.horn)

        pushButton.setOnTouchListener { _, event->
            handleTouch(event)
        }
    }

    private fun handleTouch(event: MotionEvent): Boolean {
        when (event.action){
            MotionEvent.ACTION_DOWN ->{
                mediaPlayer?.start()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP->{
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
            else->{
                println("nada")
            }
        }
        return true
    }

}