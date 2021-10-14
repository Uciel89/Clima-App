package com.example.ui_climaapp.view


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ui_climaapp.Lista.HistorialActivity
import com.example.ui_climaapp.R
import com.example.ui_climaapp.Room_DataBase.Ciudades
import com.example.ui_climaapp.Room_DataBase.CiudadesViewModel
import com.example.ui_climaapp.databinding.ActivityMainBinding
import com.example.ui_climaapp.view_model.MainViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //ViewModel y Persistencia de datos con SharedPreferences
    private lateinit var viewModel: MainViewModel

    //Variable para el CiudadViewModel -> parte de la base de datos con ROOM
    private lateinit var vCiudadViewModel: CiudadesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Implementacion del binding
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Conectamos con el MainViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //var cName = GET.getString("cityName", "rosario, ar")?.toLowerCase()
        var cName = com.example.ui_climaapp.Lista.SharedPreferences.GetStringValue(this, "cityName")

        //Valor ingresado por el EditText que nos ayuda a buscar la ciudad en tiempo real
        binding.edtCityName.setText(cName)

        //Refrescamos para que aparesca el valor introducido por el EditText
        viewModel.refreshData(cName)

        getLiveData()

        //Asignamos las funciones al swipeRefreshLayout para que refresque la pantalla
        binding.swipeRefreshLayout.setOnRefreshListener {

            binding.llData.visibility = View.GONE
            binding.tvError.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE

            //var cityName = GET.getString("cityName", cName)?.toLowerCase()
            var cityName = com.example.ui_climaapp.Lista.SharedPreferences.GetStringValue(this,
                "cityName")
            binding.edtCityName.setText(cityName)
            viewModel.refreshData(cityName)
            binding.swipeRefreshLayout.isRefreshing = false

        }

        /*Instanciamos el ciudadViewModel*/
        vCiudadViewModel = ViewModelProvider(this).get(CiudadesViewModel::class.java)

        //Añadimos un setOnClickListener a la imagen del icono de busqueda
        //Para que al valor del EditText loc coloque, primero en la permanecia de datos
        //Y segundo en el viewModel.
        binding.imgSearchCity.setOnClickListener{
            val cityName = binding.edtCityName.text.toString()
            com.example.ui_climaapp.Lista.SharedPreferences.SetStringValue(this,"cityName", cityName)
            viewModel.refreshData(cityName)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)

            InsertDataToDataBase()

        }

    }

    /*Base de datos*/

    //Funcion encarga de insertar los datos a la base de datos
    private fun InsertDataToDataBase() {

        var ciudades = binding.edtCityName.text.toString()

        if(inputCheck(ciudades)){
            //Creamos el objeto Ciudad
            val ciudad = Ciudades(0, ciudades)

            //Agregamos los datos a la base de datos
            vCiudadViewModel.addCiudad(ciudad)

           Toast.makeText(this,"El dato fue agregado con exito", Toast.LENGTH_LONG).show()
       }
    }

    //Hacemos un chequeo de los datos obtenido por el EditText
    private fun inputCheck(ciudades: String): Boolean{

        return !(TextUtils.isEmpty(ciudades))
    }

    /*Menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when (id){

            R.id.nav_historial -> {

                val intent = Intent(this, HistorialActivity::class.java)
                startActivity(intent)
            }

        }

        return true
    }

    /*GetLiveData*/

    //Creamos la funcion para obtener los datos en vivo
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
                binding.temp.text = data.main.temp.toInt().toString() + "ºC"
                binding.tempMin.text = data.main.tempMin.toInt().toString() + "ºC"
                binding.tempMax.text = data.main.tempMax.toInt().toString() + "ºC"
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





