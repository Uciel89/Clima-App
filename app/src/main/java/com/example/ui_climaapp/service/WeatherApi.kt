package com.example.ui_climaapp.service

import com.example.ui_climaapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=Rosario&appid=ccccd3ec4e5dfb77aa7da9934b71e27a

interface WeatherApi {

    //Aca usamos retrofit, puro  duro par obtener todos los datos de la Api de OpenWeather

    @GET("data/2.5/weather?&units=metric&appid=ccccd3ec4e5dfb77aa7da9934b71e27a")
    fun getData(

        //En este caso la q hace referencia a la ciudad que vamos a recurar todos sus datos meteorologicos
        @Query("q") cityName:String
    ):Single<WeatherModel>

}