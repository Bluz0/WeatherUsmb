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

var url_search = "http://api.weatherapi.com/v1/search.json?key=" + API_KEY + "&q="

class CitySearch(private val context : Context, ville_search : String ,onUpdate : () -> Unit){

    init{
        val queue = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET,
            URL + ville_search,
            null,
            { response ->
                //refresh(response)
                onUpdate()
                Log.d("cityre",response.toString())
            },

            { err ->
                println(err)
            }

        )
        queue.add(request)

    }

}
