package com.example.ui_climaapp.Lista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_climaapp.R
import com.example.ui_climaapp.Room_DataBase.CiudadesViewModel


class HistorialActivity : AppCompatActivity() {

    private lateinit var mCiudadViewModel: CiudadesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        //Establecido el RecycleView
        val adapter = RecycleViewAdapter()

        val recycleView = findViewById<RecyclerView>(R.id.recycleView)

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this)

        //Instanciamos al ViewModel
        mCiudadViewModel = ViewModelProvider(this).get(CiudadesViewModel::class.java)
        mCiudadViewModel.readAllData.observe(this, Observer { ciudad ->
            adapter.setData(ciudad)
        })
    }

}
