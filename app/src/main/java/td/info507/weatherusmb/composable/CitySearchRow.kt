package td.info507.weatherusmb.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import td.info507.weatherusmb.Separateur
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.request.CitySearchResultat
import td.info507.weatherusmb.ui.theme.WeatherUsmbTheme
import td.info507.weatherusmb.ui.theme.fondBlanc
import androidx.compose.foundation.clickable
@Composable
fun CitySearchRow(
    result: CitySearchResultat,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 35.dp,bottom = 10.dp).clickable(onClick = onClick)){
        Text(result.name + ", "+ result.region,
            modifier = Modifier.padding(top = 10.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CitySearchRowPreview() {
    WeatherUsmbTheme {
        CitySearchRow(CitySearchResultat("Marignier","Haute-Savoie","France",15.1,15.2),{})
    }
}
