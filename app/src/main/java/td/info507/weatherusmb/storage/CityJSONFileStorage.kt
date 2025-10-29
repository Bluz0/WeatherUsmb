package td.info507.weatherusmb.storage

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.utility.file.JSONFileStorage

class CityJSONFileStorage(context: Context) : JSONFileStorage<City>(context, "city") {
    override fun create(id: Int, obj: City): City {
        return City(id, obj.nameCity, obj.lat, obj.lon, obj.temp, hour = obj.hour, condition = obj.condition, meteo = obj.meteo, prevision = obj.prevision, vent = obj.vent, humidity = obj.humidity, uv = obj.uv)
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

        val conditionArray = JSONArray()
        obj.condition.forEach { temp ->
            conditionArray.put(temp)
        }
        json.put(City.CONDITION, conditionArray)

        json.put(City.METEO, obj.meteo)

        val previsionArray = JSONArray()
        obj.prevision.forEach { temp ->
            previsionArray.put(temp)
        }
        json.put(City.PREVISION, previsionArray)

        json.put(City.VENT,obj.vent)

        json.put(City.HUMIDITY, obj.humidity)
        json.put(City.UV, obj.uv)

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

        val conditionList = mutableListOf<String>()

        val conditionArray = json.optJSONArray(City.CONDITION)
        if (conditionArray != null) {
            for (i in 0 until conditionArray.length()) {
                conditionList.add(conditionArray.getString(i))
            }
        }

        val previsionList = mutableListOf<String>()

        val previsionArray = json.optJSONArray(City.PREVISION)
        if (previsionArray != null) {
            for (i in 0 until previsionArray.length()) {
                previsionList.add(previsionArray.getString(i))
            }
        }


        return City(
            json.getInt(City.ID),
            json.getString(City.NAMECITY),
            lat = json.getDouble(City.LAT),
            lon = json.getDouble(City.LON),
            temp = json.getDouble(City.TEMP),
            hour = hourList,
            condition = conditionList,
            meteo = json.optString(City.METEO, ""),
            prevision = previsionList,
            vent = json.optString(City.VENT, ""),
            humidity = json.optString(City.HUMIDITY, "0"),
            uv = json.optString(City.UV, "0.0")
            )
    }

}