package com.example.roufy235.whatsappclone.Controllers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.roufy235.whatsappclone.Adapters.MessagesRecyclerAdapter
import com.example.roufy235.whatsappclone.Model.FirebaseDatabaseChatModel
import com.example.roufy235.whatsappclone.Model.MessagesModel
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Services.Data
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chats.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.support.v7.widget.RecyclerView
import spencerstudios.com.bungeelib.Bungee


class ChatsActivity : AppCompatActivity() {

    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase

    lateinit var prefs : SharedPreferences


    lateinit var toolbar : Toolbar


    var adapter : MessagesRecyclerAdapter? = null

    lateinit var chatName : String

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        prefs = getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)
        database = FirebaseDatabase.getInstance()
        mRef = database.reference
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val bundle :  Bundle = intent.extras

        val name = bundle.getString("Name")

        chatName = bundle.getString("chatName")




        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.title = name
        //toolbar.subtitle = "new world"
        toolbar.setNavigationIcon(R.drawable.ic_action_name)
        supportActionBar!!.setIcon(R.drawable.ic_action_user)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnClickListener {

            val receiversActivityLoad = Intent(this, ReceiversProfileActivity::class.java)
            receiversActivityLoad.putExtra("Name", name)

            startActivity(receiversActivityLoad)
            Bungee.fade(this)
        }





        dummyMessages()
        adapter = MessagesRecyclerAdapter(this, Data.messageList)


        recyclerViewMessages.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerViewMessages.layoutManager = layoutManager



    }

    fun sendMessage(view : View) {
        val df = SimpleDateFormat("dd/MM/YYYY HH:mm:ss")

        val dateObj = Date()

        val message = chatTxt.text.toString()
        val date = df.format(dateObj)


        mRef.child("ChatMessages").child(chatName).push().setValue(FirebaseDatabaseChatModel(message, date, Data.getPhoneNumber(this), 0))

        //Data.messageList.add(MessagesModel(message, date, "right"))
        //adapter!!.notifyDataSetChanged()

        // Check if no view has focus:
        val keyView = this.currentFocus
        if (keyView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        chatTxt.text.clear()
    }


    fun dummyMessages() {

        mRef.child("ChatMessages").child(chatName).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0 : DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0 : DataSnapshot?) {
                try {

                    Data.messageList.clear()
                    //val dataMessage = ArrayList<MessagesModel>()

                    val value = p0!!.value as HashMap<String, Any>

                    for (key in value.keys) {
                        val uniqueId = value[key] as HashMap<String, Any>
                        if (uniqueId["sender"].toString() == Data.getPhoneNumber(this@ChatsActivity)) {
                            //println(value[key])
                            Data.messageList.add(MessagesModel(uniqueId["message"].toString(), uniqueId["date"].toString(), "right"))
                        } else {

                            //println(value[key])
                            //mRef.child("ChatMessages").child(chatName).child().child("read").setValue(1)
                            Data.messageList.add(MessagesModel(uniqueId["message"].toString(), uniqueId["date"].toString(), "left"))
                        }
                    }


                    Data.messageList.sortWith(compareBy({it.time}))

                    //Data.messageList = Data.messageList.sortedWith(compareBy({ it.time }))


                    //println(Data.messageList)

                    adapter!!.notifyDataSetChanged()

                    recyclerViewMessages.smoothScrollToPosition(Data.messageList.count())

                } catch (ex : Exception) {
                    //println(ex.message)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        when (item!!.itemId) {
            R.id.video -> {

            }
            R.id.phone -> {

            }
            R.id.view_contact -> {

            }
            R.id.media -> {

            }
            R.id.mute -> {

            }
            R.id.wallpaper -> {

            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
