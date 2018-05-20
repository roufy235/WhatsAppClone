package com.example.roufy235.whatsappclone.Controllers

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        roundedImage()

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Settings"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }



    fun accountClicked(view : View) {
        val accountLoad = Intent(this, SettingsAccountActivity::class.java)
        startActivity(accountLoad)
    }


    fun roundedImage() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.user_image)

        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

        rounded.isCircular = true

        settingsActivityUserImage.setImageDrawable(rounded)
    }

}
