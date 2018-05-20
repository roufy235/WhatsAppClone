package com.example.roufy235.whatsappclone.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title = "Verify your phone number"

    }


    fun reg(view : View) {
        imageButton.isEnabled = false
        val number = phoneNumber.text.toString()
        if (number.isNotEmpty()) {
            imageButton.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Phone number is required to complete registration", Toast.LENGTH_SHORT).show()
            imageButton.isEnabled = true
        }
    }
}
