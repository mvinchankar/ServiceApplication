package com.example.volume

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
      var system: SeekBar? = null
      var mgr: AudioManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        system = findViewById(R.id.volumeSeekBar)
        mgr = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        system!!.max = mgr!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        system!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mgr!!.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}
