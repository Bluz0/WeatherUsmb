package td.info507.weatherusmb.helper


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.*

// Data class pour stocker les coordonnées
data class LocationData(
    val latitude: Double,
    val longitude: Double
)

// Composable pour gérer la géolocalisation
@Composable
fun rememberLocationPermission(
    onLocationGranted: (LocationData) -> Unit,
    onLocationDenied: () -> Unit = {}
): () -> Unit {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(checkLocationPermission(context))
    }

    // Launcher pour demander la permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        hasPermission = granted

        if (granted) {
            getCurrentLocation(context, onLocationGranted, onLocationDenied)
        } else {
            Log.d("Location", "Permission refusée")
            onLocationDenied()
        }
    }

    // Retourne une fonction pour demander la localisation
    return {
        if (hasPermission) {
            getCurrentLocation(context, onLocationGranted, onLocationDenied)
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}

// Vérifie si la permission est accordée
fun checkLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

// Récupère la position actuelle
fun getCurrentLocation(
    context: Context,
    onSuccess: (LocationData) -> Unit,
    onError: () -> Unit
) {
    if (!checkLocationPermission(context)) {
        Log.e("Location", "Permission non accordée")
        onError()
        return
    }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    try {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location: Location? ->
            if (location != null) {
                val locationData = LocationData(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                Log.d("Location", "Position trouvée: ${location.latitude}, ${location.longitude}")
                onSuccess(locationData)
            } else {
                Log.e("Location", "Position null")
                onError()
            }
        }.addOnFailureListener { exception ->
            Log.e("Location", "Erreur: ${exception.message}")
            onError()
        }
    } catch (e: SecurityException) {
        Log.e("Location", "SecurityException: ${e.message}")
        onError()
    }
}