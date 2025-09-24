package td.info507.weatherusmb

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme

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

@Composable
fun MainWeatherScreen(){
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth().paddingFromBaseline(50.sp),){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                IconButton(onClick = {}, modifier = Modifier.padding(20.dp,0.dp).height(70.dp).width(70.dp).background(Color.LightGray)){
                    Icon(
                        painter = painterResource(R.drawable.location),
                        contentDescription = "endroit.xml",
                        modifier = Modifier.height(70.dp).width(50.dp)
                    )
                }
            }

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