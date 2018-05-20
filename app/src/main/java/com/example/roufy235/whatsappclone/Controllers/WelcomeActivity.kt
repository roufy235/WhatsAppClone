package com.example.roufy235.whatsappclone.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val text : String = "<font color=\"#ffffff\">Tap \"Agree and continue\" to accept the</font> <font color=\"#999999\">WhatsApp Terms of Services and Privacy Policy.</font>"

        agreementText.text = Html.fromHtml(text)
    }

    fun agreed(view : View) {
        startActivity(Intent(this, RegistrationActivity::class.java));
        finish()
    }
}
