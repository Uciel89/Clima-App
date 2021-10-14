package com.example.ui_climaapp.Room_DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


//La interfas Dao, basicamente contiene todos los metos de acceso a los datos de la tabla

@Dao
interface CiudadesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCiudades(ciudades: Ciudades)

    //Consulta para leer todos los datos de la tabla
    @Query("SELECT * FROM tabla_ciudades ORDER BY id ASC")
    fun readAllData(): LiveData<List<Ciudades>>
}