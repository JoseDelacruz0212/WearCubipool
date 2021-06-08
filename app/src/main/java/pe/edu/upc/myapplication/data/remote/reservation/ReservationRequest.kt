package pe.edu.upc.myapplication.data.remote.reservation

import com.google.gson.annotations.SerializedName

class ReservationRequest (

    @SerializedName("fecha")
    var date:String,

    @SerializedName("hora_inicio")
    var hourInit:String,

    @SerializedName("hora_fin")
    var hourEnd:String,

    @SerializedName("sede")
    var sede:String,

    @SerializedName("codigo_uno")
    var firstCode:String,

    @SerializedName("codigo_dos")
    var secondCode:String,

    @SerializedName("cubiculo_id")
    var cubicleId:Int,

    @SerializedName("theme")
    var theme:String

)