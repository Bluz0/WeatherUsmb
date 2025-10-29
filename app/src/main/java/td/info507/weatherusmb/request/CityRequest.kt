package td.info507.weatherusmb.request

import android.R
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.CityStorage
import java.util.Locale
import kotlin.collections.get
import kotlin.text.insert

var API_KEY = "b3a05d1bcd6443febf965205252409"
var URL = "http://api.weatherapi.com/v1/forecast.json?key="+API_KEY+"&q="

class CityRequest(private val context : Context, private val cityName: String, onUpdate : () -> Unit) {

    init{
        val queue = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET,
            URL + cityName,
            null,
            { response ->
                refresh(response)
                onUpdate()
                Toast.makeText(context,"Ville récuperer", Toast.LENGTH_SHORT).show() },
            { err ->
                println(err)
                Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT).show() }

        )
        queue.add(request)

    }

    private fun refresh(json: JSONObject) {
        insert(json)
    }

    private fun delete(){
        for (city: City in CityStorage.get(context).findAll()){

            CityStorage.get(context).delete(city.id)
        }
    }

    // JSONOBject au deb car object
    private fun insert(json : JSONObject){

        val location = json.getJSONObject("location") // Recup la liste location
        val current = json.getJSONObject("current") // recup temp mtn

        val forecast = json.getJSONObject("forecast").getJSONArray("forecastday")
        val hours_forecast = forecast.getJSONObject(0).getJSONArray("hour")//.getJSONObject(0)

        //val condition_heure = hours_forecast.getJSONObject(0).getJSONObject("condition").getString("text")
        Log.d("forecast",hours_forecast.toString())
        //Log.d("condition_heure", condition_heure)
        val hour_day_list = mutableListOf<String>()
        val condition_list = mutableListOf<String>()

        val condition_now = current.getJSONObject("condition").getString("text")

        Log.d("list_temp", condition_now)


        for (i in 0 until hours_forecast.length()) {
            hour_day_list.add(hours_forecast.getJSONObject(i).getString("temp_c") + "°C")
            condition_list.add(hours_forecast.getJSONObject(i).getJSONObject("condition").getString("text"))
        }

        Log.d("list_temp",hour_day_list.toString())
        Log.d("list_cond",condition_list.size.toString())

        val city = City(
            id = 0,
            nameCity = location.getString("name"),
            lat = location.getDouble("lat"),
            lon = location.getDouble("lon"),
            temp  = current.getDouble("temp_c"),
            hour = hour_day_list,
            condition = condition_list,
            meteo = condition_now
        )
        CityStorage.removeDuplicates(context)
        CityStorage.get(context).insert(city) // Sauvegarde json local



    }

}

// Meme classe mais avec les latitudes longitudes
class CityRequestByCoordinates(
    private val context: Context,
    private val latitude: Double,
    private val longitude: Double,
    private val onUpdate: () -> Unit
) {

    init {
        val queue = Volley.newRequestQueue(context)

        val coords = "$latitude,$longitude"

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$coords&days=1",
            null,
            { response ->
                // Récupère le nom de la ville
                getCityNameAndInsert(response)
            },
            { err ->
                Log.e("CityRequestCoords", "Erreur API: ${err.message}")
                Toast.makeText(context, "Erreur lors de la récupération", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }

    // Recup le nom de la ville via api et insert dans json
    private fun getCityNameAndInsert(json: JSONObject) {
        val location = json.getJSONObject("location")
        insertCity(json, location.getString("name"))
        onUpdate()
    }

    // saveCity : Comme insert mais nom de ville
    private fun insertCity(json: JSONObject, cityName: String) {
        val location = json.getJSONObject("location")
        val current = json.getJSONObject("current")
        val forecast = json.getJSONObject("forecast").getJSONArray("forecastday")
        val hours_forecast = forecast.getJSONObject(0).getJSONArray("hour")
        val hour_day_list = mutableListOf<String>()
        val condition_list = mutableListOf<String>()
        val condition_now = current.getJSONObject("condition").getString("text")

        for (i in 0 until hours_forecast.length()) {
            hour_day_list.add(hours_forecast.getJSONObject(i).getString("temp_c") + "°C")
            condition_list.add(hours_forecast.getJSONObject(i).getJSONObject("condition").getString("text"))
        }

        val city = City(
            id = 0,
            nameCity = cityName,
            lat = location.getDouble("lat"),
            lon = location.getDouble("lon"),
            temp = current.getDouble("temp_c"),
            hour = hour_day_list,
            condition = condition_list,
            meteo = condition_now
        )

        CityStorage.get(context).insert(city)
        CityStorage.removeDuplicates(context)
    }
}
