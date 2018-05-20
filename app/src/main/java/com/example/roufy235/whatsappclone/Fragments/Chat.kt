package com.example.roufy235.whatsappclone.Fragments

import android.content.Intent
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


class Chat : Fragment() {

    var chatsArray =  ArrayList<ChatsModel>()
    var adapter : ChatRecyclerAdapter? = null


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        dummyText()
    }
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {

        val view = inflater!!.inflate(R.layout.fragment_chat, container, false)

        val recView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)



        adapter = ChatRecyclerAdapter(context, chatsArray){chatClicked ->
            val name = chatClicked.name
            val intent = Intent(context, ChatsActivity::class.java)

            intent.putExtra("Name" , name)
            startActivity(intent)
        }
        recView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recView.layoutManager = layoutManager

        return view
    }

    fun dummyText() {
        chatsArray.clear()
        chatsArray.add(ChatsModel("bello rouf", "user_image", "07033224455: hello", "10:30 AM", "10"))
        chatsArray.add(ChatsModel("Hp", "user_two", "Hello world", "11:30 AM", "20"))
        chatsArray.add(ChatsModel("Map", "user_two", "hello new world: envy", "11:30 AM", "12"))
        chatsArray.add(ChatsModel("Hp", "user_two", "How far", "11:30 AM", "14"))
        chatsArray.add(ChatsModel("Hp", "user_two", "07033224455: envy", "11:30 AM", "18"))
    }
}