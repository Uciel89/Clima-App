package com.example.ui_climaapp.Room_DataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CiudadesViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Ciudades>>
    private val repository: CiudadesRepository

    init {
        val ciudadesDao = CiudadesDataBase.getDatabase(application).ciudadesDao()
        repository = CiudadesRepository(ciudadesDao)
        readAllData = repository.readAllData
    }

    fun addCiudad(ciudades: Ciudades){

        viewModelScope.launch(Dispatchers.IO) {

            repository.addCiudades(ciudades)
        }
    }

}