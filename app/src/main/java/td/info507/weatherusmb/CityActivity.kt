package td.info507.weatherusmb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import td.info507.weatherusmb.model.City


class CityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherUsmbTheme {
                CityScreen()
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

    LaunchedEffect(Unit) {
        // Force une requête au démarrage
        CityRequest(context) {
            citys.clear()
            citys.addAll(CityStorage.get(context).findAll())
        }
    }


    Text(citys.size.toString())

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

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        LazyColumn(modifier = Modifier.align(Alignment.TopCenter)) {

            items(citys) { city ->
                CityRow(
                    city, {},
                    {
                        //cityId.intValue = city.id
                        //openDeleteDialog.value = true
                    })

            }
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClick = { /*openCreateDialog.value = true*/ },
            containerColor = Color(68, 170, 68),
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