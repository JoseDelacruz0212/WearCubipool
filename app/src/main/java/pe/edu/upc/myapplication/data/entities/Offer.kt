package pe.edu.upc.myapplication.data.entities

import com.google.gson.annotations.SerializedName

class Offer (


    var id:Int,

    var apple:Boolean,

    @SerializedName("pizarra")
    var board: Boolean,

    @SerializedName("sitios")
    var sitios:Int,

    @SerializedName("disponible")
    var available: Boolean

)
