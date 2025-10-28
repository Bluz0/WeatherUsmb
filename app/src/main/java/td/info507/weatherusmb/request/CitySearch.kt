package td.info507.weatherusmb.request

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.CityStorage
import android.util.Log
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray

var url_search = "http://api.weatherapi.com/v1/search.json?key=" + API_KEY + "&q="

data class CitySearchResultat(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)

class CitySearch(private val context : Context, ville_search : String ,resultat : (List<CitySearchResultat>) -> Unit,onError: () -> Unit){

    init{
        val queue = Volley.newRequestQueue(context)

        val request = StringRequest(
            Request.Method.GET,
            url_search + ville_search,
            { response ->

                val resultats = ListeCity(response)
                Log.d ("CitySearch", response)
                Log.d("CitySearch", "Trouvé ${resultats.size} villes pour: $ville_search")
                //Log.d("cityre",response.getJSONObject("location").getString("name").toString())
                resultat(resultats)
            },
            { err ->
                println(err)
                onError()
            }

        )
        queue.add(request)

    }

    // Stocke les villes grace a l'output dans param (str) dans une liste
    private fun ListeCity(jsonString: String): List<CitySearchResultat> {
        val resultats = mutableListOf<CitySearchResultat>()

        try {
            val jsonArray = JSONArray(jsonString) // retransforme la str en Array avec les villes

            for (i in 0 until jsonArray.length()) {
                val cityJson = jsonArray.getJSONObject(i)

                val resultat = CitySearchResultat(
                    name = cityJson.getString("name"),
                    region = cityJson.optString("region", ""),
                    country = cityJson.getString("country"),
                    lat = cityJson.getDouble("lat"),
                    lon = cityJson.getDouble("lon")
                )
                resultats.add(resultat)
            }
        }
        catch (e: Exception) {
            Log.e("CitySearch", "Erreur parsing: ${e.message}")
        }
        return resultats
    }

}
