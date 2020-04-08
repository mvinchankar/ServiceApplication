package com.example.smsservice

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_SEND_SMS: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton: Button = findViewById(R.id.startService)
        startButton.setOnClickListener {
            sendSmsMessage()
        }
    }

    fun sendSmsMessage() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.fromParts("sms","7350055253",null)))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val smsManager : SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage("7350055253",null,"Hello",null,null)
                    Toast.makeText(applicationContext,"SMS SENT!!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"SMS FAILED!!",Toast.LENGTH_LONG).show()
                    return
                }
            }
        }

    }
}

