package com.example.roufy235.whatsappclone.Controllers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.roufy235.whatsappclone.Adapters.MessagesRecyclerAdapter
import com.example.roufy235.whatsappclone.Model.MessagesModel
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_chats.*

class ChatsActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar

    val messageList = ArrayList<MessagesModel>()
    var adapter : MessagesRecyclerAdapter? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)


        val bundle :  Bundle = intent.extras

        val name = bundle.getString("Name")

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
            Toast.makeText(this, "ToolBar clicked", Toast.LENGTH_SHORT).show()
        }





        dummyMessages()
        adapter = MessagesRecyclerAdapter(this, messageList)


        recyclerViewMessages.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        recyclerViewMessages.layoutManager = layoutManager



    }


    fun dummyMessages() {
        messageList.add(MessagesModel("Wakanda Forever", "10:11 AM", "right"))
        messageList.add(MessagesModel("you don watch am", "10:11 AM", "left"))
        messageList.add(MessagesModel("yas nah", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "left"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("no wahala", "10:11 AM", "right"))
        messageList.add(MessagesModel("hex color red value is 25, green value is 153 and the blue value of its RGB is 153. ", "10:11 AM", "right"))
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
