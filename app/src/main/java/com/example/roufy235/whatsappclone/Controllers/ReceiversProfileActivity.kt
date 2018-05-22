package com.example.roufy235.whatsappclone.Controllers

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_receivers_profile.*
import spencerstudios.com.bungeelib.Bungee

class ReceiversProfileActivity : AppCompatActivity() {


    lateinit var toolbar : Toolbar

    lateinit var switch : Switch

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receivers_profile)

        val myData : Bundle = intent.extras

        val name = myData.getString("Name")

        toolbar = findViewById(R.id.toolbar)

        switch = findViewById(R.id.notificationSwitch)

        switch.setOnClickListener {
            if (switch.isChecked) {
                Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
            }
        }


        expandedImage.setOnClickListener {
            
        }

        setSupportActionBar(toolbar)

        supportActionBar!!.title = name

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        toolbar.setNavigationOnClickListener {
            finish()
            Bungee.fade(this)
        }
    }
}
