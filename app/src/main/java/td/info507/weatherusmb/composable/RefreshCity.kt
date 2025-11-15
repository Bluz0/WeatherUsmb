package td.info507.weatherusmb.composable

import android.content.Context
import android.util.Log
import android.widget.Toast
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.request.CityRequest
import td.info507.weatherusmb.request.CityRequestByCoordinates
import td.info507.weatherusmb.storage.CityStorage

fun refreshAllCities(
    context: Context,
    citys: MutableList<City>,
    onComplete: () -> Unit
) {
    val storage = CityStorage.get(context)
    val allCities = storage.findAll().toList()

    if (allCities.isEmpty()) {
        onComplete()
        return
    }

    var refreshedCount = 0
    val totalCities = allCities.size
    val oldCitiesData = allCities.map { Triple(it.id, it.nameCity, it.lat) }  // 👈 Garde les données

    Log.d("RefreshCities", "Début refresh de $totalCities villes")
    oldCitiesData.forEach { (id, name, _) ->
        Log.d("RefreshCities", "Ville à refresh: ID=$id, Name=$name")
    }

    allCities.forEachIndexed { index, city ->
        if (index == 0) {
            Log.d("RefreshCities", "Refresh GPS: ${city.nameCity} (ID: ${city.id})")
            CityRequestByCoordinates(context, city.lat, city.lon) {
                refreshedCount++
                Log.d("RefreshCities", "GPS refreshed: $refreshedCount/$totalCities")

                if (refreshedCount == totalCities) {
                    finalizeRefresh(context, storage, oldCitiesData, citys, onComplete)
                }
            }
        } else {
            Log.d("RefreshCities", "Refresh by name: ${city.nameCity} (ID: ${city.id})")
            CityRequest(context, city.nameCity) {
                refreshedCount++
                Log.d("RefreshCities", "City refreshed: $refreshedCount/$totalCities")

                if (refreshedCount == totalCities) {
                    finalizeRefresh(context, storage, oldCitiesData, citys, onComplete)
                }
            }
        }
    }
}

private fun finalizeRefresh(
    context: Context,
    storage: td.info507.weatherusmb.storage.utility.Storage<City>,
    oldCitiesData: List<Triple<Int, String, Double>>,
    citys: MutableList<City>,
    onComplete: () -> Unit
) {
    // Attendre pour être sûr que les insertions soient finis
    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
        Log.d("RefreshCities", "Finalisation...")

        val beforeDelete = storage.findAll()
        Log.d("RefreshCities", "Avant suppression: ${beforeDelete.size} villes")
        beforeDelete.forEach { city ->
            Log.d("RefreshCities", "  - ${city.id}:${city.nameCity}")
        }

        // Vérifie qu'on a bien de nouvelles villes avant de suppr
        val newCitiesCount = beforeDelete.count { city ->
            oldCitiesData.none { (oldId, _, _) -> oldId == city.id }
        }

        Log.d("RefreshCities", "Nouvelles villes détectées: $newCitiesCount")

        if (newCitiesCount >= oldCitiesData.size) {

            oldCitiesData.forEach { (oldId, oldName, _) ->
                storage.delete(oldId)
                Log.d("RefreshCities", "Deleted old: $oldId:$oldName")
            }
        } else {
            Log.e("RefreshCities", "ATTENTION: Pas assez de nouvelles villes! Annulation de la suppression.")
        }

        val afterDelete = storage.findAll()
        Log.d("RefreshCities", "Après suppression: ${afterDelete.size} villes")
        afterDelete.forEach { city ->
            Log.d("RefreshCities", "  - ${city.id}:${city.nameCity}")
        }

        // Nettoie les doublons
        CityStorage.removeDuplicates(context)

        // Met à jour l'UI
        citys.clear()
        citys.addAll(storage.findAll())

        Log.d("RefreshCities", "Villes finales: ${citys.size}")
        citys.forEach { city ->
            Log.d("RefreshCities", "Ville finale: ${city.id}:${city.nameCity}")
        }

        onComplete()
        //Toast.makeText(context, "Données mises à jour (${citys.size} villes)", Toast.LENGTH_SHORT).show()
    }, 1500)
}