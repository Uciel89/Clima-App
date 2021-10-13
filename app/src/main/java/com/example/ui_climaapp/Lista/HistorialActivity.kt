package com.example.ui_climaapp.Lista

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_climaapp.R
import com.example.ui_climaapp.databinding.ActivityMainBinding
import com.example.ui_climaapp.view.MainActivity

class HistorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        //var recyclerViewAdapter = RecycleViewAdapter(arrayList)

        //setupRecycleView(recyclerViewAdapter)
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

private fun SharedPreferences.all(): Historial {

    return Historial(getString("city", "Rosario, AR").toString())

}
