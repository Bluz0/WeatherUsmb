package td.info507.weatherusmb.storage

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.utility.file.JSONFileStorage

class CityJSONFileStorage(context: Context) : JSONFileStorage<City>(context, "city") {
    override fun create(id: Int, obj: City): City {
        return City(id, obj.nameCity, obj.lat, obj.lon, obj.temp, hour = obj.hour)
    }

    override fun objectToJson(id: Int, obj: City): JSONObject {
        val json = JSONObject()
        json.put(City.ID, obj.id)
        json.put(City.NAMECITY,obj.nameCity)
        json.put(City.LAT,obj.lat)
        json.put(City.LON,obj.lon)
        json.put(City.TEMP,obj.temp)

        val hourArray = JSONArray()
        obj.hour.forEach { temp ->
            hourArray.put(temp)
        }
        json.put(City.HOUR, hourArray)

        return json
    }

    override fun jsonToObject(json: JSONObject): City {

        val hourList = mutableListOf<String>()

        val hourArray = json.optJSONArray(City.HOUR)
        if (hourArray != null) {
            for (i in 0 until hourArray.length()) {
                hourList.add(hourArray.getString(i))
            }
        }

        return City(
            json.getInt(City.ID),
            json.getString(City.NAMECITY),
            lat = json.getDouble(City.LAT),
            lon = json.getDouble(City.LON),
            temp = json.getDouble(City.TEMP),
            hour = hourList
            )
    }

}