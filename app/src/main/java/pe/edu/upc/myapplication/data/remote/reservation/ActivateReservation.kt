package pe.edu.upc.myapplication.data.remote.reservation

import com.google.gson.annotations.SerializedName

class ActivateReservation (

    @SerializedName("codigo")
    var code: String,

    @SerializedName("reservaId")
    var reservationId:Int

)
