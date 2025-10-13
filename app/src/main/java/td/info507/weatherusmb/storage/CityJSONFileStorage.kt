package td.info507.weatherusmb.storage

import android.content.Context
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.utility.file.JSONFileStorage

class CityJSONFileStorage(context: Context) : JSONFileStorage<City>(context, "city") {
    override fun create(id: Int, obj: City): City {
        return City(id, obj.nameCity, obj.lat, obj.lon, obj.temp)
    }

    override fun objectToJson(id: Int, obj: City): JSONObject {
        val json = JSONObject()
        json.put(City.ID, obj.id)
        json.put(City.NAMECITY,obj.nameCity)
        json.put(City.LAT,obj.lat)
        json.put(City.LON,obj.lon)
        json.put(City.TEMP,obj.temp)


        return json
    }

    override fun jsonToObject(json: JSONObject): City {
        return City(
            json.getInt(City.ID),
            json.getString(City.NAMECITY),
            lat = json.getDouble(City.LAT),
            lon = json.getDouble(City.LON),
            temp = json.getDouble(City.TEMP)
            )
    }

}