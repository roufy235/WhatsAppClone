package com.example.roufy235.whatsappclone.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Services.Data
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_settings.*
import spencerstudios.com.bungeelib.Bungee

class SettingsActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar
    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        database = FirebaseDatabase.getInstance()
        mRef = database.reference
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        roundedImage()

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Settings"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    fun loadProfile(view : View) {
        val intent = Intent(this, ChangeProfileActivity::class.java)
        startActivity(intent)
        Bungee.fade(this)
    }

    fun accountClicked(view : View) {
        val accountLoad = Intent(this, SettingsAccountActivity::class.java)
        startActivity(accountLoad)
    }


    fun roundedImage() {
        mRef.child("contacts").child("profileImage").child(Data.getPhoneNumber(this)).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0 : DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0 : DataSnapshot) {
                if (p0.value != null) {
                    val  path = p0.value as String

                    Picasso
                            .get()
                            .load(path)
                            .transform(CropCircleTransformation())
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .into(settingsActivityUserImage)
                } else {
                    settingsActivityUserImage.setImageResource(R.drawable.ic_person_black_24dp)
                }

            }
        })
    }
}
