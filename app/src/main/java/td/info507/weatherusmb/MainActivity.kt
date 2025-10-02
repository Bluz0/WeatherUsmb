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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


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
                    text = "Le Bourget-Du-Lac",
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
                modifier = Modifier
            )

        }


    }


}


@Preview(showBackground = true)
@Composable
fun WeatherMainPreview() {
    WeatherUsmbTheme {
        MainWeatherScreen()
    }
}