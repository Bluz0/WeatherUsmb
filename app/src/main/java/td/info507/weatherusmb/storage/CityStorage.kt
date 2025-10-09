package td.info507.weatherusmb.storage

import android.content.Context
import android.content.SharedPreferences
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



}