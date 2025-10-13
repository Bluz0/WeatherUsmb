package td.info507.weatherusmb.model

class City (val id : Int = 0,val nameCity : String, val lat : Double, val lon : Double, val temp : Double) {

    companion object{
        const val ID = "id"
        const val NAMECITY = "name"
        const val LAT = "lat"
        const val LON = "lon"

        const val TEMP = "temp_c"
    }

}