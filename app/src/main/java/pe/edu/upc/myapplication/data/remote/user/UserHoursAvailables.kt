package pe.edu.upc.myapplication.data.remote.user

import com.google.gson.annotations.SerializedName

class UserHoursAvailables (
    @SerializedName("horasDisponibles")
    var hoursAvailables: Int
)