package td.info507.weatherusmb.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import td.info507.weatherusmb.CityScreen
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme
import td.info507.weatherusmb.ui.theme.fondBlanc
import td.info507.weatherusmb.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityRow(
    city: City,
    onClick: (City) -> Unit,
    onLongClick: (City) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(fondBlanc)
            .combinedClickable(
                onClick = { onClick(city) },
                onLongClick = { onLongClick(city) }
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.location),
            contentDescription = "Location",
            modifier = Modifier.size(70.dp)
        )
        Text(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            text = city.nameCity,
            textAlign = TextAlign.Center

        )

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
