package td.info507.weatherusmb.request

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.CityStorage
import kotlin.collections.get
import kotlin.text.insert
var API_KEY = "b3a05d1bcd6443febf965205252409"
var URL = "http://api.weatherapi.com/v1/forecast.json?key="+API_KEY+"&q="
var test_nom = "Le Bourget-Du-Lac"

/*
pour avoir le nom de la ville avec localisation du phone prcq dans param q, ya pas lat,lon
public String getLocationName(double lattitude, double longitude) {

    String cityName = "Not Found";
    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
    try {

        List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
        10);

        for (Address adrs : addresses) {
            if (adrs != null) {

                String city = adrs.getLocality();
                if (city != null && !city.equals("")) {
                    cityName = city;
                    System.out.println("city ::  " + cityName);
                } else {

                }
                // // you should also try with addresses.get(0).toSring();

            }

        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return cityName;

}

 */

class CityRequest(private val context : Context, onUpdate : () -> Unit) {

    init{
        val queue = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://api.weatherapi.com/v1/forecast.json?key=b3a05d1bcd6443febf965205252409&q=Marignier&days=1",
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



    // JSONOBject au deb car object
    private fun insert(json : JSONObject){

        val location = json.getJSONObject("location") // Recup la liste location


        val city = City(
            nameCity = location.getString("name"),
            lat = location.getDouble("lat"),
            lon = location.getDouble("lon")
        )
        CityStorage.get(context).insert(city) // Sauvegarde json local

    }



}