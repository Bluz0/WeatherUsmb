package td.info507.weatherusmb.composable

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import td.info507.weatherusmb.CityScreen
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme
import td.info507.weatherusmb.ui.theme.fondBlanc
import td.info507.weatherusmb.R
import td.info507.weatherusmb.storage.CityStorage


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityRow(
    city: City,
    onClick: (City) -> Unit,
    onLongClick: (City) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp,20.dp,20.dp,0.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(fondBlanc, shape = RoundedCornerShape(18.dp))
            .combinedClickable(
                onClick = { onClick(city) },
                onLongClick = { onLongClick(city) }
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_location_on_24),
            contentDescription = "Location",
            modifier = Modifier.size(60.dp).align(Alignment.CenterVertically).padding(start = 20.dp)
        )

        Text(
            style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        ), modifier = Modifier
            .padding(20.dp, 27.dp, 20.dp, 0.dp),
            text = city.nameCity,
            textAlign = TextAlign.Center
        )

        Log.d("logcityid",city.id.toString())

        if (city.id != 1){
            // TODO : créer une sorte de refresh quand on delete, prcq ca suppr pas a l'affichage
            IconButton(
                onClick = { CityStorage.get(context).delete(city.id)},
                modifier = Modifier.paddingFromBaseline(13.dp)

            ){
                Icon(
                    painterResource(R.drawable.outline_delete_24),
                    contentDescription = "poubelle",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
        // besoin du else pour enlever poubelle ?

        // pk pas mettre lat et lon
    }
}

@Preview(showBackground = true)
@Composable
fun CityRowPreview() {
    WeatherUsmbTheme {
        CityRow(City(0,"Le Bourget-Du-Lac",0.0,0.0,),{},{})
    }
}
