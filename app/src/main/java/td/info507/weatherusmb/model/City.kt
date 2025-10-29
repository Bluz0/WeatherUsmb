package td.info507.weatherusmb.model

import android.R

class City (val id : Int = 0,val nameCity : String, val lat : Double, val lon : Double, val temp : Double, val hour : MutableList<String>, val condition : MutableList<String>, val meteo : String) {

    companion object{
        const val ID = "id"
        const val NAMECITY = "name"
        const val LAT = "lat"
        const val LON = "lon"

        const val TEMP = "temp_c"

        const val HOUR = "hour"

        const val CONDITION = "condition"

        const val METEO = "meteo"
    }

}