package pe.edu.upc.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.data.remote.ApiClient
import pe.edu.upc.myapplication.data.remote.reservation.ReservationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CubicleViewModel : ViewModel(){

    private val cubiclesAvailables: List<Cubicle> = ArrayList()
    var _cubiclesAvailables = MutableLiveData<List<Cubicle>>()

    private var _dateForSubmit = MutableLiveData<String>()

    private var _date = MutableLiveData<String>()
    private var _hour = MutableLiveData<String>()
    private var _qHours = MutableLiveData<String>()
    private var _helpHourEnd = MutableLiveData<Int>()
    private var _hourEnd = MutableLiveData<String>()

    var firstTimeSelected = MutableLiveData<Boolean>()
    var isCubicleSelected = MutableLiveData<Boolean>()
    var lastCubicle = MutableLiveData<Cubicle>()

    var isCorrect = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    fun startVariables(date: String,hour:String,qHours:String){
        _date.value = date
        _hour.value = hour
        _qHours.value = qHours

        _helpHourEnd.value =(hour.take(2).toInt() + qHours.toInt())
        if (_helpHourEnd.value!! < 10){
            _hourEnd.value = "0${_helpHourEnd.value!!}:00"
        }else{
            _hourEnd.value = "${_helpHourEnd.value!!}:00"
        }
    }

    fun startDateSubmit(){
        updateDate(_date.value!!)
    }

    private fun updateDate(date: String) {
        if (date == "Hoy"){
            _dateForSubmit.value = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        }else if(date == "Mañana"){
            _dateForSubmit.value = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_DATE)
        }
    }

    fun searchAllCubicle(){
        val findAllCubicle = ApiClient.build()?.findAllAvailableCubicles(_date.value!!,_hour.value!!,
            _qHours.value!!)

        findAllCubicle?.enqueue(object : Callback<ArrayList<Cubicle>>{
            override fun onResponse(
                call: Call<ArrayList<Cubicle>>,
                response: Response<ArrayList<Cubicle>>
            ) {
                if (response.isSuccessful){
                    _cubiclesAvailables.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<Cubicle>>, t: Throwable) {
                Log.i("CViewModel: ", "ERROR")
            }

        })
    }

    fun reservationSubmit(firstCode: String, secondCode: String, idCubicle:Int){
        val reservationRequest = ReservationRequest(_dateForSubmit.value!!,_hour.value!!,_hourEnd.value!!,"Monterrico",firstCode,secondCode,idCubicle,"Calculo 2")
        val reservationCubicle = ApiClient.build()?.submitReservation(reservationRequest)
        Log.i("SUBMITdate: ",reservationRequest.date)
        Log.i("SUBMIThour: ",reservationRequest.hourInit)
        Log.i("SUBMITEnd: ",reservationRequest.hourEnd)
        Log.i("SUBMITECODE ",reservationRequest.firstCode)
        Log.i("SUBMITECODE ",reservationRequest.secondCode)
        Log.i("SUBMITECubicle ",reservationRequest.cubicleId.toString())

        reservationCubicle?.enqueue(object: Callback<ReservationRequest>{
            override fun onResponse(
                call: Call<ReservationRequest>,
                response: Response<ReservationRequest>
            ) {
                if (response.isSuccessful){
                    isCorrect.value = true
                }else{
                    message.value = "Error"
                }
            }

            override fun onFailure(call: Call<ReservationRequest>, t: Throwable) {
                Log.i("error", "Algo salio mal")
                message.value = "Algo salio mal"
        }

        })
    }
}