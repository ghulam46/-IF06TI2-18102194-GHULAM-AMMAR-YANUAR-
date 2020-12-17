package com.ghulam_18102194.praktikum8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_sms_receiver.*

class SmsReceiverActivity : AppCompatActivity() {
    val keywords = arrayOf("hadiah","blogspot","wordpress","pulsa","selamat","transfer","mobil","polisi","rumah")
    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_receiver)
        title = getString(R.string.incoming_message)
        val senderNo = intent.getStringExtra(com.ghulam_18102194.praktikum8.SmsReceiverActivity.EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(com.ghulam_18102194.praktikum8.SmsReceiverActivity.EXTRA_SMS_MESSAGE)
        val match = keywords.filter { it in senderMessage!!.split(" ") }
        if(match.isNotEmpty()){
            activity_sms_receiver.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        }

        senderMessage?.let {
            var wordcount = 0;
            keywords.map { word ->
                if (it.contains(word, true)){
                    wordcount++
                }
            }
            println("forbiddenWordCount $wordcount")

            if (wordcount >= 1){
                Toast.makeText(this, "Hati_hati!!! Sms Ini Penipuan!!!", Toast.LENGTH_SHORT).show()
            }
        }
        tv_from.text = getString(R.string.coming_from)+": "+senderNo
        tv_message.text = senderMessage
        btn_close.setOnClickListener { finish() }
    }
}