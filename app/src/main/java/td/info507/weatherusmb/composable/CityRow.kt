package td.info507.weatherusmb.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import td.info507.weatherusmb.model.City
import td.info507.weatherusmb.ui.theme.fondBlanc


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
        Text(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            text = city.nameCity,
            textAlign = TextAlign.Center

        )

        // pk pas mettre lat et lon
    }
}