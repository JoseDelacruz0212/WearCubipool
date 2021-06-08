package pe.edu.upc.myapplication.data.entities

class Cubicle (
    val id: Int,
    val name: String,
    val startTime: String,
    val endTime: String,
    val day:String,
    var status: Boolean = false
)