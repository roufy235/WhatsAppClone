package com.example.roufy235.whatsappclone.Controllers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase


    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title = "Verify your phone number"


        prefs = getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)

        database = FirebaseDatabase.getInstance()

        mRef = database.reference


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

    }


    fun reg(view : View) {
        imageButton.isEnabled = false
        val number = phoneNumber.text.toString()
        if (number.isNotEmpty()) {


            saveToSharedPreferences(number)

            saveToDatabase(number)


            imageButton.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Phone number is required to complete registration", Toast.LENGTH_SHORT).show()
            imageButton.isEnabled = true
        }
    }


    private fun saveToSharedPreferences(phone : String) {
        prefs.edit().apply {

            putString("phone", phone)
            putBoolean("login", true)

            apply()
        }
    }

    fun saveToDatabase(phone : String) {
        mRef.child("contacts").child(phone).setValue(phone)
        mRef.child("userChatList").child(phone).setValue(true)
    }
}
