package com.example.roufy235.whatsappclone.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.roufy235.whatsappclone.Model.ChatsModel
import com.example.roufy235.whatsappclone.R

class ChatRecyclerAdapter(val context : Context, val chats : ArrayList<ChatsModel>, val itemClicked : (ChatsModel) -> Unit) : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ticket_chat, parent, false)
        return ViewHolder(view, itemClicked)
    }

    override fun getItemCount() : Int {
        return chats.count()
    }

    override fun onBindViewHolder(holder : ViewHolder?, position : Int) {
        holder!!.bindView(context, chats[position])
    }


    inner class ViewHolder(itemView : View?,  val itemClicked : (ChatsModel) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val image : ImageView = itemView!!.findViewById(R.id.userImage)
        val name : TextView = itemView!!.findViewById(R.id.tvUsername)
        val messages : TextView = itemView!!.findViewById(R.id.tvMessages)
        val time : TextView = itemView!!.findViewById(R.id.tvTime)
        val counter : TextView = itemView!!.findViewById(R.id.tvMessageCounter)


        fun bindView(context : Context, chats : ChatsModel) {
            val resId = context.resources.getIdentifier(chats.image, "drawable", context.packageName)

            val bitmap = BitmapFactory.decodeResource(context.resources, resId)

            val rounded = RoundedBitmapDrawableFactory.create(context.resources, bitmap)

            rounded.isCircular = true


            itemView.setOnClickListener { itemClicked(chats) }



            image.setImageDrawable(rounded)

            name.text = chats.name
            messages.text = chats.message
            time.text = chats.time
            counter.text = chats.counter
        }
    }
}