package com.example.ui_climaapp.Room_DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

//Basicamente la Entity son las entiedades que va a contener nuestra base de datos
@Entity(tableName = "tabla_ciudades")
data class Ciudades (

    //Estas entidades van a contener el valor de ciudades
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ciudades: String

    )
