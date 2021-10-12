package com.example.ui_climaapp.service

import com.example.ui_climaapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class WeatherApiService {

    //https://api.openweathermap.org/data/2.5/weather?q=Rosario&appid=ccccd3ec4e5dfb77aa7da9934b71e27a

    //BASE_URL -> La url de la pagina de openweathermap
    private val BASE_URL = "https://api.openweathermap.org/"

    //Armamos el Retrofit
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getDataService(cityName: String):Single<WeatherModel>{
        return api.getData(cityName)
    }

}