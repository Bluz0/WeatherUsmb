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
import androidx.compose.ui.Alignment
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.input.pointer.pointerInput


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherUsmbTheme {
                MainWeatherScreen()
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
fun MainWeatherScreen(){
    val context = LocalContext.current

    // Box = la page
    Box(modifier = Modifier.background(Brush.verticalGradient(listOf(Color(255,156,157),Color(170,172,255)))).fillMaxSize()){
        // Row = la nav bar abaissé
        Row(modifier = Modifier.fillMaxWidth().paddingFromBaseline(30.sp)){
            // Interieur nav bar avec btn localisation (image et couleur a changer)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                IconButton(onClick = {}, modifier = Modifier.padding(20.dp,0.dp).height(40.dp).width(40.dp).background(
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

        // TODO : Jour choisi et date format jj/mm/aaaa
        Column(modifier = Modifier.paddingFromBaseline(80.dp).padding(20.dp,0.dp).height(50.dp).width(100.dp)){
            Text(text = "Dimanche", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(29,29,29))
            Text(text = "02/10/2025",
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
                painter = painterResource(R.drawable.location),
                contentDescription = "icon loc",
                modifier = Modifier.background(fondBlanc).padding(10.dp, 0.dp).fillMaxHeight()
                    .width(50.dp).size(29.dp)
            )
            Row(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .background(fondBlanc, shape = RoundedCornerShape(0.dp, 18.dp, 18.dp, 0.dp))
            ) {
                Text(
                    // TODO : remplacer texte par api
                    text = "Jaja",
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
        Row(modifier = Modifier.paddingFromBaseline(270.dp).fillMaxWidth().padding(30.dp,0.dp,30.dp,0.dp)){

            Icon(
                painterResource(R.drawable.clear_day),
                "",
                modifier = Modifier.size(80.dp)
            )

        }

        Column(modifier = Modifier.paddingFromBaseline(400.dp).fillMaxWidth().height(120.dp).padding(20.dp,0.dp,20.dp,0.dp).background(fondBlanc, shape = RoundedCornerShape(10.dp))){
            Text("Prévision sur 24 heures",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp

                ),
                color = Color(67,67,67),
                modifier = Modifier.padding(10.dp,10.dp)
            )

            val testtemp = arrayOf("0°","1°","2°","3°","4°","5°","6°","7°","8°","9°","10°","11°","12°","13°","14°","15°","16°","17°","18°","19°","20°","21°","22°","23°")
            val hours = arrayOf("00h","01h","02h","03h","04h","05h","06h","07h","08h","09h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h")
            val itemWidth = 60.dp

            val listStateTemp = rememberLazyListState()
            val listStateHour = rememberLazyListState()


            // Quand on scroll en haut, ca scroll celui du bas
            LaunchedEffect(listStateTemp,listStateHour) {
                snapshotFlow { listStateTemp.firstVisibleItemIndex to listStateTemp.firstVisibleItemScrollOffset }
                    .collect { (index,offset) ->
                        listStateHour.scrollToItem(index,offset)

                    }

            }

            LazyRow(state = listStateTemp, modifier = Modifier.fillMaxWidth()) {
                items(testtemp) {
                    temp ->
                    Box(modifier = Modifier.width(itemWidth).padding(4.dp,0.dp,0.dp,0.dp),
                        contentAlignment = Alignment.Center){
                        Text(temp, modifier = Modifier)
                    }

                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            LazyRow(state = listStateHour) {
                items(hours){
                    hour ->
                    Box(modifier = Modifier.width(itemWidth).padding(4.dp,0.dp,0.dp,0.dp),
                        contentAlignment = Alignment.Center){
                        Text(hour)
                    }
                }
            }

        }


    }


}

// Param à voir selon type que l'api nous transmet
@Composable
fun hourRightNow(heureMtn : String){

}


@Preview(showBackground = true)
@Composable
fun WeatherMainPreview() {
    WeatherUsmbTheme {
        MainWeatherScreen()
    }
}