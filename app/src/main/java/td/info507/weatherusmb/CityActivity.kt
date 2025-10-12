package td.info507.weatherusmb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import td.info507.weatherusmb.composable.CityRow
import td.info507.weatherusmb.request.CityRequest
import td.info507.weatherusmb.storage.CityStorage
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.ui.theme.fondBlanc
import td.info507.weatherusmb.helper.*
import td.info507.weatherusmb.request.CityRequestByCoordinates
import td.info507.weatherusmb.request.getCityNameFromCoordinates


class CityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherUsmbTheme {
                CityScreenList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityScreenList() {
    val context = LocalContext.current

    val citys = remember { mutableStateListOf<City>() }
    val isRefreshing = remember { mutableStateOf(false) }
    val openAddDialog = remember { mutableStateOf(false) }
    val isLoadingGPS = remember { mutableStateOf(false) }

    // Configuration de la géolocalisation
    val requestLocation = rememberLocationPermission(
        onLocationGranted = { location ->
            isLoadingGPS.value = true
            Log.d("GPS", "Position: ${location.latitude}, ${location.longitude}")

            CityRequestByCoordinates(context, location.latitude, location.longitude) {
                citys.clear()
                citys.addAll(CityStorage.get(context).findAll())
                isLoadingGPS.value = false
            }
        },
        onLocationDenied = {
            isLoadingGPS.value = false
            Toast.makeText(context, "Permission GPS refusée", Toast.LENGTH_SHORT).show()
        }
    )



    LaunchedEffect(Unit) {
        // cityRequest force une requête au démarrage et ajoute via api
        //CityRequest(context) {
        citys.clear()
        citys.addAll(CityStorage.get(context).findAll())

        if (citys.isEmpty()) {
            requestLocation()
        }

        //}
    }


    val longueur_citys = citys.size
    if (longueur_citys == 0) {
        Text(citys.size.toString())
    }


    /*PullToRefreshBox(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        isRefreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
            CityRequest(context) {
                citys.clear()
                citys.addAll(CityStorage.get(context).findAll())
                isRefreshing.value = false
            }
        }){ */

    Box(modifier = Modifier.background(Brush.verticalGradient(listOf(Color(255,156,157),Color(170,172,255)))).fillMaxWidth().fillMaxHeight()) {
        LazyColumn(modifier = Modifier.fillMaxHeight().align(Alignment.TopCenter).paddingFromBaseline(180.dp)) {
            items(citys) { city ->
                CityRow(
                    city, {cityChoisi ->
                        val intent = Intent(context, MainActivity::class.java).apply {
                            putExtra("cityName", cityChoisi.nameCity)
                            putExtra("cityLat", cityChoisi.lat)
                            putExtra("cityLon", cityChoisi.lon)
                            putExtra("cityId", cityChoisi.id)
                        }
                        context.startActivity(intent)
                    },

                    {}
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { /*openCreateDialog.value = true*/ },
            containerColor = fondBlanc,
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "Large floating action button")
        }
    }



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityScreen(){
    val context = LocalContext.current

    CityScreenList()

}



@Preview(showBackground = true)
@Composable
fun CityPreview() {
    WeatherUsmbTheme {
        CityScreen()
    }
}