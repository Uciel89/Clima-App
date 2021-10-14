package com.example.ui_climaapp.Room_DataBase

import androidx.lifecycle.LiveData

class CiudadesRepository(private val ciudadesDao: CiudadesDao) {

    val readAllData: LiveData<List<Ciudades>> = ciudadesDao.readAllData()

    suspend fun addCiudades(ciudades: Ciudades){
        ciudadesDao.addCiudades(ciudades)
    }


}