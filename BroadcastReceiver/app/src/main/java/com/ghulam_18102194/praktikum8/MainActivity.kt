package com.ghulam_18102194.praktikum8

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val SMS_REQUEST_CODE = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS),
                        SMS_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults:
    IntArray) {
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

}