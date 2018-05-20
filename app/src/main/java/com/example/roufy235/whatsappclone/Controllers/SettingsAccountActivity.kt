package com.example.roufy235.whatsappclone.Controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.roufy235.whatsappclone.R

class SettingsAccountActivity : AppCompatActivity() {


    lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_account)

        toolbar = findViewById(R.id.accountToolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Account"
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
    }
}
