package com.example.ui_climaapp.Lista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_climaapp.R


class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        var historialList = mutableListOf<Historial>(

            Historial(SharedPreferences.GetStringValue(this,"cityName"))

        )

        var recyclerViewAdapter = RecycleViewAdapter(historialList)

        setupRecycleView(recyclerViewAdapter)
    }

    //Inicializamos al RecycleView
    private fun setupRecycleView(recyclerViewAdapter: RecycleViewAdapter) {

        //Configuracion del RecycleView
        val recycleView = findViewById<RecyclerView>(R.id.recycleView)

        recycleView.apply {

            //layoutManager -> nos sirve para colocar nuestros items dentro del recycleView
            layoutManager = LinearLayoutManager(this@HistorialActivity)

            adapter = recyclerViewAdapter
        }
    }
}
