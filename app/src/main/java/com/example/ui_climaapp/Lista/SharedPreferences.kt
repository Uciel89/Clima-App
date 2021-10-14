package com.example.ui_climaapp.Lista

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {

    private val KEY = "ciudadKey"

    //Funcion para guardar elementos dentro del SharedPreference
    fun SetStringValue (context: Context, key: String?, save: String?){

        val sharedPre: SharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)

        //editor -> es el que nos permite agregar valores al SharedPreference
        val editor: SharedPreferences.Editor = sharedPre.edit()
        editor.putString(key, save)
        editor.apply()

    }

    //Funcion que nos permite traer los valores del SharedPreference
    fun GetStringValue(context: Context, key: String?): String {

        //Obtenemos los valores guardado en nuestro SharedPreference
        val sharedPref: SharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)

        return sharedPref.getString(key, "")!!

    }


}
