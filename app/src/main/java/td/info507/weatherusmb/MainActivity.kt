package td.info507.weatherusmb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme
import td.info507.weatherusmb.ui.theme.fondBlanc
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import td.info507.weatherusmb.model.City
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cityId = intent.getIntExtra("cityId", 0)
        val cityName = intent.getStringExtra("cityName") ?: "Ville inconnue"
        val cityLat = intent.getDoubleExtra("cityLat", 0.0)
        val cityLon = intent.getDoubleExtra("cityLon", 0.0)
        val cityTemp = intent.getDoubleExtra("cityTemperature", 0.0)

        setContent {
            WeatherUsmbTheme {
                MainWeatherScreen(cityName, cityLat,cityLon, cityTemp)
                //MainWeatherScreen()
                //CityScreen()
            }
        }
    }
}

/*
@Composable
fun DisplayMed(ma_str : String, modifier: Modifier, couleur : Color) {
    //Text(text = ma_str,modifier = Modifier, color = couleur, fontSize = 45.dp, lineHeight = 52.dp , letterSpacing = 0.dp)
    MaterialTheme(
        colorScheme = "",
        typography =


    )
}*/

@Composable
fun Separateur(){
    HorizontalDivider(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        //color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        thickness = 1.dp,
        color = Color(67,67,67)
    )
}

@Composable
fun prevision_jour(TextJour : String, tempBas : String, tempHaut : String){
    Row(modifier = Modifier.paddingFromBaseline(10.dp).padding(start=5.dp,end=5.dp).fillMaxWidth()){
        Text(TextJour,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp
            ),
            modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp).align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Icon(
                painter = painterResource(R.drawable.thermometer),
                tint = Color.Unspecified,
                contentDescription = "",
                modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp).size(35.dp)
            )
            Text(
                "$tempBas° - $tempHaut°", textAlign = TextAlign.Center,
                modifier = Modifier.padding(0.dp, 0.dp, 15.dp, 0.dp)
                    .align(alignment = Alignment.CenterVertically)
            )

        }
    }
}


@Composable
fun MainWeatherScreen(cityName: String,
                      cityLat: Double = 0.0,
                      cityLon: Double = 0.0,
                      cityTemp : Double){
    val context = LocalContext.current

    // Box = la page
    Box(modifier = Modifier.background(Brush.verticalGradient(listOf(Color(255,156,157),Color(170,172,255)))).fillMaxSize().verticalScroll(rememberScrollState())){
        // Row = la nav bar abaissé
        Row(modifier = Modifier.fillMaxWidth().paddingFromBaseline(30.sp)){
            // Interieur nav bar avec btn localisation (image et couleur a changer)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                IconButton(onClick = {
                    val intent = Intent(context, CityActivity::class.java)
                    context.startActivity(intent)
                }, modifier = Modifier.padding(20.dp,0.dp).height(40.dp).width(40.dp).background(
                    fondBlanc,
                    RoundedCornerShape(7.dp))){
                    Icon(
                        painter = painterResource(R.drawable.location),
                        contentDescription = "endroit.xml",
                        modifier = Modifier.height(26.dp).width(26.dp)
                    )
                }
            }
        }

        val date_aujourdhui_format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date_aujourdhui = date_aujourdhui_format.format(Date())

        val jour_format = SimpleDateFormat("EEEE",Locale.getDefault())
        val majuscule = jour_format.format(Date()).substring(0,1).uppercase()
        val jour = majuscule + jour_format.format(Date()).substring(1)

        Column(modifier = Modifier.paddingFromBaseline(80.dp).padding(20.dp,0.dp).height(50.dp).width(100.dp)){
            Text(text = jour, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(29,29,29))
            Text(text = date_aujourdhui,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                color = Color(29,29,29)
                )
        }

        // Ligne incluant icon et nom de la ville
        Row(modifier = Modifier.paddingFromBaseline(180.dp).height(100.dp)
            .padding(20.dp,0.dp,20.dp,0.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(fondBlanc, shape = RoundedCornerShape(18.dp))
            ) {
            Icon(
                painter = painterResource(R.drawable.baseline_location_on_24),
                contentDescription = "icon loc",
                modifier = Modifier.background(fondBlanc).padding(10.dp, 0.dp).fillMaxHeight()
                    .width(50.dp).size(29.dp)
            )
            Row(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .background(fondBlanc, shape = RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp))
            ) {
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.fillMaxHeight().padding(5.dp, 37.dp, 20.dp, 0.dp)
                        .background(fondBlanc)
                )
            }
        }

        // Row : icon meteo + temperature
        Row(modifier = Modifier.paddingFromBaseline(300.dp).fillMaxWidth().padding(30.dp,0.dp,30.dp,0.dp)){
            // Remplace par fct ?
            Icon(
                painter = painterResource(R.drawable.clear_day),
                "",
                tint = Color.Unspecified,
                modifier = Modifier.padding(start=0.dp).size(110.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$cityTemp°C",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color(27, 27, 27),
                        fontSize = 60.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                // row pour mettre icon avec txt vent
                Text(
                    "11 km/h",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(27, 27, 27)
                    ),
                    fontSize = 16.sp
                )
            }



        }

        Column(modifier = Modifier.paddingFromBaseline(400.dp).fillMaxWidth().height(150.dp).padding(20.dp,0.dp,20.dp,0.dp).background(fondBlanc, shape = RoundedCornerShape(10.dp))){
            Text("Prévision sur 24 heures",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp

                ),
                color = Color(67,67,67),
                modifier = Modifier.padding(10.dp,10.dp)
            )

            // gere temp + image icon + heure
            // TODO : temp api
            // TODO : mettre en place la fct pour enlever les heures avec heures actuel

            val testtemp = arrayOf("0°","1°","2°","3°","4°","5°","6°","7°","8°","9°","10°","11°","12°","13°","14°","15°","16°","17°","18°","19°","20°","21°","22°","23°")
            var hours = arrayOf("00h","01h","02h","03h","04h","05h","06h","07h","08h","09h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h")
            val itemWidth = 60.dp



            LazyRow(modifier = Modifier.fillMaxWidth()) {
                // Remplace icon par fct
                items(testtemp.size) {
                    i ->
                    Box(modifier = Modifier.width(itemWidth).fillMaxHeight()){
                        // text : les temps
                        Text(testtemp[i], modifier = Modifier.fillMaxWidth().padding(5.dp,0.dp,0.dp,0.dp),textAlign = TextAlign.Center)
                        Icon(
                            painter = painterResource(R.drawable.clear_day),
                            contentDescription = "icon meteo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp).align(alignment = Alignment.Center)
                        )
                        Text(hours[i],
                            style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        ), color = Color(67,67,67),
                            modifier = Modifier.align(Alignment.BottomEnd).fillMaxWidth().padding(bottom = 5.dp),
                            textAlign = TextAlign.Center)
                    }
                }

            }
        }

        Column(modifier = Modifier.align(Alignment.Center).paddingFromBaseline(350.dp).fillMaxWidth().height(153.dp).padding(20.dp,0.dp,20.dp,0.dp).background(fondBlanc,RoundedCornerShape(10.dp))){
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp,10.dp)){
                Icon(
                    painter = painterResource(R.drawable.calendar),
                    contentDescription = "Calendrier.xmltxtjspkoi",
                    modifier = Modifier.size(15.dp)
                )
                Text("Prévision sur 3 jours",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp

                    ),
                    color = Color(67,67,67),
                    modifier = Modifier.padding(5.dp,0.dp)
                )
            }

            prevision_jour("Jeu.","18","24")
            Separateur()
            prevision_jour("Ven.","17","25")
            Separateur()
            prevision_jour("Sam.","8", tempHaut = "14")
        }

        Column(Modifier.fillMaxWidth().align(Alignment.Center).paddingFromBaseline(800.dp).padding(start = 20.dp,end = 220.dp).height(200.dp).background(fondBlanc,RoundedCornerShape(10.dp))){
            Text("UV",
                style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
                color = Color(67,67,67),
                modifier = Modifier.padding(15.dp,10.dp)
            )

            Box(Modifier.fillMaxWidth().fillMaxHeight()){
                Text("Faible", modifier = Modifier.padding(15.dp,0.dp))
                Icon(
                    painter = painterResource(R.drawable.clear_day),
                    tint = Color.Unspecified,
                    contentDescription = "a voir.xml",
                    modifier = Modifier.padding(top = 30.dp).fillMaxWidth().fillMaxHeight()
                )
            }
        }

        Column(Modifier.fillMaxWidth().align(Alignment.Center).paddingFromBaseline(800.dp).padding(start = 220.dp,end = 20.dp).height(200.dp).background(fondBlanc,RoundedCornerShape(10.dp))){
            Text("Humidité",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                color = Color(67,67,67),
                modifier = Modifier.padding(15.dp,10.dp)
            )

            Box(Modifier.fillMaxWidth().fillMaxHeight()){
                Text("76%", modifier = Modifier.padding(15.dp,0.dp))
                Icon(
                    painter = painterResource(R.drawable.humidity),
                    tint = Color.Unspecified,
                    contentDescription = "a voir.xml",
                    modifier = Modifier.padding(top = 30.dp).fillMaxWidth().fillMaxHeight()
                )
            }
        }


    }


}

// Param à voir selon type que l'api nous transmet
@Composable
fun hourRightNow(heureMtn : String,hours : List<String>){
    // TODO : avec heure en param, enlever les heures selon heure param, du type si on a 15h, on fera 23-15 ou 14 jsp, pour nous afficher 15h et heures restant de la journée

}


@Preview(showBackground = true)
@Composable
fun WeatherMainPreview() {
    WeatherUsmbTheme {
        MainWeatherScreen(cityName = "test", cityLat = 0.0, cityLon = 0.0, cityTemp = 0.0)
        //CityScreen()
    }
}