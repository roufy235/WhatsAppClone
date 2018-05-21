package com.example.roufy235.whatsappclone.Controllers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        prefs = getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)

        val loginAlready : Boolean = prefs.getBoolean("login", false)
        //val phoneNumber : Int = prefs.getInt("phone", 0)


        if (loginAlready) {
            startActivity(Intent(this, MainActivity::class.java))
        }


        val text = "<font color=\"#ffffff\">Tap \"Agree and continue\" to accept the</font> <font color=\"#999999\">WhatsApp Terms of Services and Privacy Policy.</font>"

        agreementText.text = Html.fromHtml(text)
    }

    fun agreed(view : View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }
}
