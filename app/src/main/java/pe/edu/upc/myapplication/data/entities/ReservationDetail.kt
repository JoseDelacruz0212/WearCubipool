package pe.edu.upc.myapplication.data.entities

import com.google.gson.annotations.SerializedName

class ReservationDetail (

    @SerializedName("cubiculoNombre")
    var cubicleName: String,
    @SerializedName("horaInicio")
    var hourInit: String,
    @SerializedName("horaFin")
    var hourEnd: String,
    @SerializedName("tema")
    var theme: String,

    @SerializedName("estado")
    var status: String,

    @SerializedName("sitiosDisponible")
    var sitsAvailable: Int,

    @SerializedName("sede")
    var campus: String,

    @SerializedName("participantes")
    var participants: ArrayList<ParticipantsReservation>,

    @SerializedName("activate")
    var activate:String,

    @SerializedName("offer")
    var offer:ArrayList<Offer>,

    @SerializedName("rol")
    var role:String

)