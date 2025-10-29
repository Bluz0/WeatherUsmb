package td.info507.weatherusmb.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import td.info507.weatherusmb.R

@Composable
fun TextToLogo(Etat : String) : Painter {

    return painterResource(
        id = when (Etat){
            "Sunny" -> R.drawable.clear_day
            "Clear" -> R.drawable.clear_night
            "Clear " -> R.drawable.clear_night
            "Partly cloudy" -> R.drawable.partly_cloudy_day
            "Cloudy" -> R.drawable.cloudy
            "Overcast" -> R.drawable.overcast
            "Mist" -> R.drawable.mist
            "Patchy rain possible" -> R.drawable.rain
            "Patchy snow possible" -> R.drawable.snow
            "Patchy sleet possible" -> R.drawable.sleet
            "Patchy freezing drizzle possible" -> R.drawable.rain
            "Thundery outbreaks possible" -> R.drawable.hurricane
            "Blowing snow" -> R.drawable.snow
            "Blizzard" -> R.drawable.hurricane
            "Fog" -> R.drawable.fog
            "Freezing Fog" -> R.drawable.fog
            "Patchy light drizzle" -> R.drawable.rain
            "Light drizzle" -> R.drawable.rain
            "Freezing drizzle" -> R.drawable.rain
            "Heavy freezing drizzle" -> R.drawable.rain
            "Patchy light rain" -> R.drawable.rain
            "Light rain" -> R.drawable.rain
            "Moderate rain at times" -> R.drawable.rain
            "Moderate rain" -> R.drawable.rain
            "Heavy rain at times" -> R.drawable.rain
            "Heavy rain" -> R.drawable.rain
            "Light freezing rain" -> R.drawable.rain
            "Moderate or heavy freezing rain" -> R.drawable.rain
            "Light sleet" -> R.drawable.sleet
            "Moderate or heavy sleet" -> R.drawable.sleet
            "Patchy light snow" -> R.drawable.snow
            "Light snow" -> R.drawable.snow
            "Patchy moderate snow" -> R.drawable.snow
            "Moderate snow" -> R.drawable.snow
            "Patchy heavy snow" -> R.drawable.snow
            "Heavy snow" -> R.drawable.snow
            "Ice pellets" -> R.drawable.hail
            "Light rain shower" -> R.drawable.rain
            "Moderate or heavy rain shower" -> R.drawable.rain
            "Torrential rain shower" -> R.drawable.rain
            "Light sleet showers" -> R.drawable.sleet
            "Moderate or heavy sleet showers" -> R.drawable.sleet
            "Light snow showers" -> R.drawable.snow
            "Moderate or heavy snow showers" -> R.drawable.snow
            "Light showers of ice pellets" -> R.drawable.snow
            "Moderate or heavy showers of ice pellets" -> R.drawable.snow
            "Patchy light rain with thunder" -> R.drawable.thunderstorms
            "Moderate or heavy rain with thunder" -> R.drawable.thunderstorms
            "Patchy light snow with thunder" -> R.drawable.thunderstorms
            "Moderate or heavy snow with thunder" -> R.drawable.thunderstorms

            else -> R.drawable.cloudy
        }
    )
}