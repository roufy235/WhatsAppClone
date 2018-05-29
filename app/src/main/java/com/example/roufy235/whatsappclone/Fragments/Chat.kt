package com.example.roufy235.whatsappclone.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roufy235.whatsappclone.Adapters.ChatRecyclerAdapter
import com.example.roufy235.whatsappclone.Controllers.ChatsActivity
import com.example.roufy235.whatsappclone.Model.ChatsModel
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Services.Data
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration.*
import spencerstudios.com.bungeelib.Bungee


class Chat : Fragment() {

    var adapter : ChatRecyclerAdapter? = null

    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase

    lateinit var prefs : SharedPreferences


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = context.getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)
        database = FirebaseDatabase.getInstance()
        mRef = database.reference
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)


        dummyText()
    }
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        val view = inflater!!.inflate(R.layout.fragment_chat, container, false)

        val recView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)



        adapter = ChatRecyclerAdapter(context, Data.chatsArray){chatClicked ->
            val name = chatClicked.name
            val number : String = chatClicked.number
            val intent = Intent(context, ChatsActivity::class.java)

            val sender = number.substring(3, 8)
            val rec = Data.getPhoneNumber(context).substring(3, 8)

            val chatName = (sender.toInt() + rec.toInt()).toString()

            intent.putExtra("Name" , name)
            intent.putExtra("chatName" , chatName)


            mRef.child("userChatList").child(Data.getPhoneNumber(context)).child(number).child("chatName").setValue(chatName)
            mRef.child("userChatList").child(number).child(Data.getPhoneNumber(context)).child("chatName").setValue(chatName)

            startActivity(intent)
            //Bungee.fade(context)
        }
        recView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recView.layoutManager = layoutManager

        return view
    }

    fun dummyText() {
        Data.chatsArray.clear()

        mRef.child("userChatList").child(Data.getPhoneNumber(context)).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0 : DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0 : DataSnapshot) {
                try {
                    val value = p0.value as HashMap<String, Any>

                    Data.chatsArray.clear()

                    for (key in value.keys) {

                        val uniqueId = value[key] as HashMap<String, String>

                        Data.chatsArray.add(ChatsModel(uniqueId["name"].toString(), "user_image", "07033224455: hello", "10:30 AM", "10", uniqueId["number"].toString()))
                    }

                    //println(Data.chatsArray)

                    adapter!!.notifyDataSetChanged()

                } catch (ex : Exception) {
                   // println(ex.message)
                }
            }

        })
    }
}