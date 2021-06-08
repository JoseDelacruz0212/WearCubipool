package pe.edu.upc.myapplication.data.entities

import com.google.gson.annotations.SerializedName

class ParticipantsReservation (

    @SerializedName("codigo")
    var code:String,

    @SerializedName("nombre")
    var name:String
)
