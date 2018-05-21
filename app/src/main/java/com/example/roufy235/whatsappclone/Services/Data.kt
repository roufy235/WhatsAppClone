package com.example.roufy235.whatsappclone.Services

import android.content.Context
import android.content.SharedPreferences
import com.example.roufy235.whatsappclone.Model.ChatsModel
import com.example.roufy235.whatsappclone.Model.MessagesModel
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN

object Data {

    lateinit var prefs : SharedPreferences


    val chatsArray =  ArrayList<ChatsModel>()

    var messageList = ArrayList<MessagesModel>()


    fun getPhoneNumber(context : Context) : String {
        prefs = context.getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)

        return prefs.getString("phone", " ")
    }
}