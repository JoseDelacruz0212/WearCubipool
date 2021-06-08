package pe.edu.upc.myapplication.data.remote.reservation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class UserReservationsAvailables (

    var id: Int,
    var name: String,
    var startTime: String,
    var endTime: String,
    var day: String,
    var status: String

)
