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
import com.example.roufy235.whatsappclone.Model.CallerModel
import com.example.roufy235.whatsappclone.R

class CallRecyclerAdapter(val  context : Context, val callLogs : ArrayList<CallerModel>) : RecyclerView.Adapter<CallRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : ViewHolder {
        val view  = LayoutInflater.from(context).inflate(R.layout.call_logs, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() : Int {
        return callLogs.count()
    }

    override fun onBindViewHolder(holder : ViewHolder?, position : Int) {
        holder!!.bindView(context, callLogs[position])
    }


    inner class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView) {
        val callerImage : ImageView = itemView!!.findViewById(R.id.callerImage)
        val callerName : TextView = itemView!!.findViewById(R.id.callCallerName)
        val callerTime : TextView = itemView!!.findViewById(R.id.callTime)



        fun bindView(context : Context, logs : CallerModel) {
            val resId = context.resources.getIdentifier(logs.image, "drawable", context.packageName)

            val bitmap = BitmapFactory.decodeResource(context.resources, resId)

            val rounded = RoundedBitmapDrawableFactory.create(context.resources, bitmap)

            rounded.isCircular = true


            callerImage.setImageDrawable(rounded)
            callerName.text = logs.name
            callerTime.text = logs.date
        }
    }
}