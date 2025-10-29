package td.info507.weatherusmb.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.storage.utility.Storage


object CityStorage {
    private const val STORAGE = "storage"
    const val FILE_JSON = 0

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("td.info507.weatherusmb.preferences", Context.MODE_PRIVATE)
    }

    fun getStorage(context: Context): Int {
        return getPreferences(context).getInt(STORAGE, FILE_JSON)
    }

    fun setStorage(context: Context, prefStorage: Int) {
        getPreferences(context).edit().putInt(STORAGE,prefStorage).apply()
    }

    fun get(context: Context): Storage<City> {
        lateinit var storage: Storage<City>
        when (getStorage(context)) {
            FILE_JSON -> storage = CityJSONFileStorage(context)
        }
        return storage
    }

    fun clearAll(context: Context) {
        val storage = get(context)
        storage.findAll().forEach { city ->
            storage.delete(city.id)
        }
        Log.d("CityStorage", "Toutes les villes supprimées")
    }



    fun removeDuplicates(context: Context) {
        val storage = get(context)
        val allCities = storage.findAll()

        // Groupe les villes par nom
        val cityMap = mutableMapOf<String, City>()
        val idsToDelete = mutableListOf<Int>()

        allCities.forEach { city ->
            val cityNameLower = city.nameCity.lowercase()

            if (cityMap.containsKey(cityNameLower)) {
                // Doublon trouvé, on garde celui avec le plus petit ID
                idsToDelete.add(city.id)
                Log.d("CityStorage", "Doublon trouvé: ${city.nameCity} (ID: ${city.id})")
            } else {
                cityMap[cityNameLower] = city
            }
        }

        // Supprime les doublons
        idsToDelete.forEach { id ->
            storage.delete(id)
        }
    }



}