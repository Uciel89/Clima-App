package com.example.ui_climaapp.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ui_climaapp.model.WeatherModel
import com.example.ui_climaapp.service.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {

    private val weatherApiService = WeatherApiService()
    private val disposable = CompositeDisposable()

    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_loading = MutableLiveData<Boolean>()

    //Funcion para refrescar el nombre de la ciudad
    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)

    }

    private fun getDataFromAPI(cityName: String) {

        weather_loading.value = true
        disposable.add(

            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    //Tenemos dos metodos, el onSuccess y el onError, estos se van a ejecutar segun el estado de
                    //la obtencion de los datos del la api, si la obtencion es exitosa, se ejecuta onSuccess
                    //Pero si la obtencion no se puede realizar, se ejecuta onError
                    override fun onSuccess(t: WeatherModel) {

                        //weather_data -> se trata de todos los datos obtenidos de OpenWeather
                        //Se ejecuta si la obtencion es exitosa
                        weather_data.value = t

                        //weather_error -> hace referencia cunado la obtencion es fallida
                        weather_error.value = false

                        //weather_loadind -> seria la carga de los datos
                        //Esta en falso, porque ya la ejecutamos al principio de todo
                        weather_loading.value = false
                        Log.d(TAG, "El proceso fue exitoso")
                    }

                    override fun onError(e: Throwable) {
                        //Se activa weather_error porque la obtención de los datos no fue exitosa
                        weather_error.value = true
                        weather_loading.value = false
                        Log.e(TAG, "Error: " + e)
                    }

                })

        )

    }

}