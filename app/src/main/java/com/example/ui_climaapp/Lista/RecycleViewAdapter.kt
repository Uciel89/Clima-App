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
import kotlinx.android.synthetic.main.region.view.*

class RecycleViewAdapter: RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    private var myCiudadesList = emptyList<Ciudades>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.region, parent, false))
    }

    override fun getItemCount(): Int {

        return myCiudadesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = myCiudadesList[position]

        holder.itemView.id_text.text = currentItem.id.toString()
        holder.itemView.ciudad_txt.text = currentItem.ciudades

    }

    fun setData(ciudad: List<Ciudades>){
        this.myCiudadesList = ciudad
        notifyDataSetChanged()
    }


}