package pe.edu.upc.myapplication.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CubicleF (

    val firstCode:String,
    val hour:String,
    val qHours: String,
    val date:String,
    val secondCode:String

): Parcelable