package com.example.roufy235.whatsappclone.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.roufy235.whatsappclone.Model.MessagesModel
import com.example.roufy235.whatsappclone.R

class MessagesRecyclerAdapter(val context : Context, val messagesList : ArrayList<MessagesModel>) : RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : ViewHolder {

        val view : View

        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.ticket_message_right, parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.ticket_message_left, parent, false)
        }


        return ViewHolder(view)
    }

    override fun getItemCount() : Int {
        return messagesList.count()
    }

    override fun onBindViewHolder(holder : ViewHolder?, position : Int) {
        holder!!.bindView(messagesList[position])
    }

    override fun getItemViewType(position : Int) : Int {
        val messageListPosition = messagesList[position]

        if (messageListPosition.placement == "right") {
            return 1
        } else if (messageListPosition.placement == "left") {
            return 2
        } else {
            return 2
        }
    }


    inner class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView) {
        val time : TextView = itemView!!.findViewById(R.id.ticket_time)
        val message : TextView = itemView!!.findViewById(R.id.ticket_message)

        fun bindView(chatMessages : MessagesModel) {
            time.text = chatMessages.time
            message.text = chatMessages.message
        }
    }
}