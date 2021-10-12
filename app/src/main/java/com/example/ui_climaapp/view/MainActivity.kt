package com.example.ui_climaapp.view


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ui_climaapp.R
import com.example.ui_climaapp.databinding.ActivityMainBinding
import com.example.ui_climaapp.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //ViewModel y Persistencia de datos con SharedPreferences
    private lateinit var viewModel: MainViewModel
    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Implementacion del binding
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Asignando los valores a GET y SET -> Para hacer uso de la permanencia de datos
        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        //Conectamos con el MainViewModel
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cName = GET.getString("cityName", "rosario, ar")?.toLowerCase()

        //Valor ingresado por el EditText que nos ayuda a buscar la ciudad en tiempo real
        binding.edtCityName.setText(cName)

        //Refrescamos para que aparesca el valor introducido por el EditText
        viewModel.refreshData(cName!!)

        getLiveData()

        //Asignamos las funciones al swipeRefreshLayout para que refresque la pantalla
        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.llData.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE

            var cityName = GET.getString("cityName", cName)?.toLowerCase()
            binding.edtCityName.setText(cityName)
            viewModel.refreshData(cityName!!)
            binding.swipeRefreshLayout.isRefreshing = false

        }

        //Añadimos un setOnClickListener a la imagen del icono de busqueda
        //Para que al valor del EditText loc coloque, primero en la permanecia de datos
        //Y segundo en el viewModel.
        binding.imgSearchCity.setOnClickListener{
            val cityName = binding.edtCityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewModel.refreshData(cityName)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)
        }

    }

    //Creamos la funcion para obtener los datos en vivo
    @SuppressLint("SetTextI18n")
    private fun getLiveData(){
        viewModel.weather_data.observe(this, Observer { data ->
            data?.let {

                binding.llData.visibility = View.VISIBLE

                //Nombre de la ciudad a la cual estamos haciendo la llamada de sus datos del clima
                binding.tvCiudad.text = data.name

                //Utilizamos Glide para poder obtener la imagen del estado del clima de OpenWeatherMap
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(binding.imageViewEstado)

                //El estado propiamente dicho del clima
                binding.estadoDelClima.text = data.weather.get(0).description

                /*Demas valores de la Activity del clima*/
                binding.temp.text = data.main.temp.toInt().toString() + R.string.ºc
                binding.tempMin.text = data.main.tempMin.toInt().toString() + R.string.ºc
                binding.tempMax.text = data.main.tempMax.toInt().toString() + R.string.ºc
                binding.humedad.text = data.main.humidity.toString() + "%"
                binding.viento.text = data.wind.speed.toString()
            }
        })

        //Si la obtencion de datos es erronea
        viewModel.weather_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    tv_error.visibility = View.VISIBLE
                    pb_loading.visibility = View.GONE
                    ll_data.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })

        viewModel.weather_loading.observe(this, Observer { loading ->

            loading?.let {

                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    ll_data.visibility = View.GONE

                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        })
    }

}





