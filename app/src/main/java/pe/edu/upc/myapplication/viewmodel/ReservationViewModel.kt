package pe.edu.upc.myapplication.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.data.entities.ReservationDetail
import pe.edu.upc.myapplication.data.remote.ApiClient
import pe.edu.upc.myapplication.data.remote.reservation.ActivateReservation
import pe.edu.upc.myapplication.data.remote.reservation.UserReservationsAvailables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationViewModel : ViewModel() {

    var qReservation = MutableLiveData<Int>()

    //val code = "u202120211"
    val code = MutableLiveData<String>()

    var myCubiclesAvailables = MutableLiveData<List<UserReservationsAvailables>>()


    var myCubicleDetail = MutableLiveData<ReservationDetail>()
    var idReservation = MutableLiveData<Int>()
    var isActivatedReservation = MutableLiveData<Boolean>()

    fun getQuantityReservationsAvailable(){

        val getQReservation = ApiClient.build()?.getReservationsAvailables(code.value!!)

        getQReservation?.enqueue(object : Callback<ArrayList<UserReservationsAvailables>>{
            override fun onResponse(
                call: Call<ArrayList<UserReservationsAvailables>>,
                response: Response<ArrayList<UserReservationsAvailables>>
            ) {
                qReservation.postValue(response.body()?.size)
                myCubiclesAvailables.postValue(response.body())

            }

            override fun onFailure(
                call: Call<ArrayList<UserReservationsAvailables>>,
                t: Throwable
            ) {
                Log.i("VM ERROR: ","No se pudo cargar las reservas")
            }


        })

    }

    fun getReservation(){
        val reservation = ApiClient.build()?.findReservationById(idReservation.value!!,code.value!!)

        reservation?.enqueue(object : Callback<ReservationDetail>{
            override fun onResponse(
                call: Call<ReservationDetail>,
                response: Response<ReservationDetail>
            ) {
                if (response.isSuccessful){
                    myCubicleDetail.postValue(response.body())
                }else{
                    Log.i("ERROR: ","ERROR")
                }

            }

            override fun onFailure(call: Call<ReservationDetail>, t: Throwable) {
                Log.i("FALLO: ","FALLO")
            }

        })
    }

    fun activateReservation(){
        val activate = ApiClient.build()?.activateCubicle(ActivateReservation(code.value!!,idReservation.value!!))

        activate?.enqueue(object: Callback<ActivateReservation>{
            override fun onResponse(
                call: Call<ActivateReservation>,
                response: Response<ActivateReservation>
            ) {
                isActivatedReservation.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ActivateReservation>, t: Throwable) {
                Log.i("FALLO2: ","FALLO")

            }

        })
    }


}