package com.example.ui_climaapp.Lista

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_climaapp.R
import com.example.ui_climaapp.Room_DataBase.Ciudades

class RecycleViewAdapter(private val myArrayList: List<Historial>):
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    private var mContext: Context? = null

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){

        var ciudad: TextView = v.findViewById(R.id.historial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Variable de los items
        val itemView = LayoutInflater.from(parent.context)

            //Inflamos a nuestro row_layout, el parent es el MainActivity
            .inflate(R.layout.region, parent,false)

        mContext = parent.context

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.ciudad.text = myArrayList[position].historial
    }

    override fun getItemCount(): Int {

        return myArrayList.size
    }

}