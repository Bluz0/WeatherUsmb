package td.info507.weatherusmb.request

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
var test_nom = "Le Bourget-Du-Lac"

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
        delete()
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


        val city = City(
            id = 0,
            nameCity = location.getString("name"),
            lat = location.getDouble("lat"),
            lon = location.getDouble("lon")
        )
        CityStorage.get(context).insert(city) // Sauvegarde json local

        CityStorage.removeDuplicates(context)

    }



}

class CityRequestByCoordinates(
    private val context: Context,
    private val latitude: Double,
    private val longitude: Double,
    private val onUpdate: () -> Unit
) {

    init {
        // Récupère les données météo avec les coordonnées
        fetchWeatherByCoordinates()
    }

    private fun fetchWeatherByCoordinates() {
        val queue = Volley.newRequestQueue(context)

        // L'API accepte les coordonnées au format "lat,lon"
        val coords = "$latitude,$longitude"

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$coords&days=1",
            null,
            { response ->
                // Étape 2 : Récupère le nom de la ville via Geocoder
                getCityNameAndSave(response)
            },
            { err ->
                Log.e("CityRequestCoords", "Erreur API: ${err.message}")
                Toast.makeText(context, "Erreur lors de la récupération", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }


    private fun getCityNameAndSave(json: JSONObject) {
        getCityNameFromCoordinates(context, latitude, longitude) { cityName ->
            if (cityName != null) {
                // Étape 3 : Sauvegarde avec le nom traduit
                saveCity(json, cityName)
                onUpdate()
                Toast.makeText(context, "Ville ajoutée: $cityName", Toast.LENGTH_SHORT).show()
            } else {
                // Fallback : utilise le nom de l'API
                val location = json.getJSONObject("location")
                saveCity(json, location.getString("name"))
                onUpdate()
            }
        }
    }

    private fun saveCity(json: JSONObject, cityName: String) {
        val location = json.getJSONObject("location")

        val city = City(
            id = 0,
            nameCity = cityName, // Utilise le nom du Geocoder
            lat = location.getDouble("lat"),
            lon = location.getDouble("lon")
        )

        CityStorage.get(context).insert(city)
        Log.d("CityRequestCoords", "Ville insérée: $cityName (${city.lat}, ${city.lon})")

        CityStorage.removeDuplicates(context)
    }
}

// Récupère le nom d'une ville via coordonnées
fun getCityNameFromCoordinates(
    context: Context,
    latitude: Double,
    longitude: Double,
    onResult: (String?) -> Unit
) {
    try {
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                val cityName = addresses.firstOrNull()?.locality
                    ?: addresses.firstOrNull()?.subAdminArea
                    ?: addresses.firstOrNull()?.adminArea
                    ?: "Ville inconnue"

                onResult(cityName)
                Log.d("Geocoder", "Ville trouvée: $cityName")
            }
        } else {
            // Android 12 et moins
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val cityName = addresses?.firstOrNull()?.locality
                ?: addresses?.firstOrNull()?.subAdminArea
                ?: addresses?.firstOrNull()?.adminArea
                ?: "Ville inconnue"

            onResult(cityName)
            Log.d("Geocoder", "Ville trouvée: $cityName")
        }
    } catch (e: Exception) {
        Log.e("Geocoder", "Erreur: ${e.message}")
        onResult(null)
    }
}