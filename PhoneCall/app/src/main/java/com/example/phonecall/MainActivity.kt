package com.example.phonecall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    var editText: EditText? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        button!!.setOnClickListener(View.OnClickListener { makeCall() })
    }

    private fun makeCall() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(
                                this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity,
                            arrayOf(Manifest.permission.CALL_PHONE), 101);
                    return
                }
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + editText!!.text.toString())
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + editText!!.text.toString())
                startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
