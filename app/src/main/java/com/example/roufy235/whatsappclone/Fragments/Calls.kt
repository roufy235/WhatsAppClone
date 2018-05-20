package com.example.roufy235.whatsappclone.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roufy235.whatsappclone.Adapters.CallRecyclerAdapter
import com.example.roufy235.whatsappclone.Model.CallerModel
import com.example.roufy235.whatsappclone.R


class Calls : Fragment() {

    val callLogs = ArrayList<CallerModel>()
    var adapter : CallRecyclerAdapter? = null


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        callerDummy()
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {



        val view = inflater!!.inflate(R.layout.fargment_calls, container, false)


        val recView : RecyclerView = view.findViewById(R.id.callLogsRecycler)


        adapter = CallRecyclerAdapter(activity, callLogs)

        recView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)

        recView.layoutManager = layoutManager
        return view

    }


    fun callerDummy() {
        callLogs.add(CallerModel("user_image", "May 14, 2018 10:09 AM", "bello"))
        callLogs.add(CallerModel("user_image", "May 14, 2018 10:09 AM", "bello"))

    }
}