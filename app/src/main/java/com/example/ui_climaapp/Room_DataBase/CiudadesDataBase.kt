package com.example.ui_climaapp.Room_DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ciudades::class], version = 1, exportSchema = false)
abstract class CiudadesDataBase: RoomDatabase() {

    abstract fun ciudadesDao(): CiudadesDao

    companion object {
        @Volatile
        private var INSTANCE: CiudadesDataBase? = null

        fun getDatabase(context: Context): CiudadesDataBase{

            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CiudadesDataBase::class.java,
                    "ciudades_database"

                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }



}