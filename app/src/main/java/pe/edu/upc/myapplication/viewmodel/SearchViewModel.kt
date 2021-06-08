package pe.edu.upc.myapplication.viewmodel

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.upc.myapplication.R
import pe.edu.upc.myapplication.data.remote.ApiClient
import pe.edu.upc.myapplication.data.remote.user.UserHoursAvailables
import pe.edu.upc.myapplication.data.remote.user.UserResponse
import pe.edu.upc.myapplication.util.ReservationUtilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {


    val util = ReservationUtilities()


    //////////SET HOURS ////////////////
    private val hoursAvailable: MutableList<String> = ArrayList()
    var hAvailable = MutableLiveData<MutableList<String>>()
    val _hourSelected = MutableLiveData<String>()
    ///////////////////////////////////

    ////////// SET DAYS ////////
    val _daySelected = MutableLiveData<String>()
    private val _daysAvailables: MutableList<String> = ArrayList()
    var daysAvailables = MutableLiveData<MutableList<String>>()
    ///////////////////////////////////

    ////////// QUANTITY HOURS ////////////
    var hoursToday = MutableLiveData<Int>()
    var hoursTomorrow = MutableLiveData<Int>()
    private val _hours: MutableList<String> = ArrayList()
    var hours = MutableLiveData<MutableList<String>>()
    val _qhourSelected = MutableLiveData<String>()
    /////////////////////////////////////////

    //////////////// Search Cubicles ////////////////
    var status = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()
    /////////////////////////////////////////


    fun startHour(hourSelected:String){
        _hourSelected.value = hourSelected
    }

    fun startDay(daySelected:String){
        _daySelected.value = daySelected
    }

    fun startQuantityHours(qhoursSelected: String){
        _qhourSelected.value = qhoursSelected
    }

    fun findHoursAvailableToday(code:String){
        val  userFindHoursResponse = ApiClient.build()?.getHoursAvailablesByDay(code,"Hoy")
        userFindHoursResponse?.enqueue(object: Callback<UserHoursAvailables> {
            override fun onResponse(
                call: Call<UserHoursAvailables>,
                response: Response<UserHoursAvailables>
            ) {
                if(response.body()?.hoursAvailables != 0){
                    if(util.actualHour < 22){
                        hoursToday.value = response.body()?.hoursAvailables
                        var isAdd = false
                        for (i in _daysAvailables){
                            if(i == "Hoy")
                                isAdd = true
                        }
                        if (!isAdd) {
                            _daysAvailables.add("Hoy")
                            daysAvailables.postValue(_daysAvailables)
                        }

                    }
                }
                findHoursAvailableTomorrow(code)
            }

            override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {

            }

        })
    }

    private fun findHoursAvailableTomorrow(code:String){
        val  userFindHoursTomorrowResponse = ApiClient.build()?.getHoursAvailablesByDay(code,"Mañana")

        userFindHoursTomorrowResponse?.enqueue(object: Callback<UserHoursAvailables>{
            override fun onResponse(
                call: Call<UserHoursAvailables>,
                response: Response<UserHoursAvailables>
            ) {
                if (response.body()?.hoursAvailables != 0){
                    hoursTomorrow.value = response.body()?.hoursAvailables
                    var isAdd = false
                    for (i in _daysAvailables){
                        if(i == "Mañana")
                            isAdd = true
                    }
                    if (!isAdd) {
                        _daysAvailables.add("Mañana")
                        daysAvailables.postValue(_daysAvailables)
                    }

                }
            }

            override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {

            }

        })
    }

    fun setHours(date: String){
        if (hAvailable.value == null) {
            hoursAvailable.clear()
            util.hourValidation(hoursAvailable,date)
            hAvailable.postValue(hoursAvailable)
            Log.i("HOURS: ",hoursAvailable.toString())
        }
    }

    fun setQuantityHours(date:String){
        _hours.clear()
        if(date == "Hoy"){
            if (hoursToday.value == 2){
                _hours.add("1")
                _hours.add("2")
            }else if (hoursToday.value == 1){
                _hours.add("1")
            }
        }else if (date == "Mañana"){
            if (hoursTomorrow.value == 2){
                _hours.add("1")
                _hours.add("2")
            }else if (hoursTomorrow.value == 1){
                _hours.add("1")
            }
        }
        hours.postValue(_hours)
    }




    //////////////// Search Cubicles ////////////////

    fun searchCubiclesAvailable(secondUserCode:String){
        val userSearchUser = ApiClient.build()?.findById(secondUserCode)

        userSearchUser?.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                when (response.code()){
                    404 -> {
                        Log.i("gg", "gg")

                        message.value = "El usuario no se encuentra registrado"
                    }
                    200 -> findAvailableHoursForSecondUser(secondUserCode)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Error", "Hubo un error la obtener el usuario")
            }


        })

    }

    private fun findAvailableHoursForSecondUser(secondUserCode:String){
        val  userFindHoursResponse = ApiClient.build()?.getHoursAvailablesByDay(secondUserCode,_daySelected.value!!)

        userFindHoursResponse?.enqueue(object : Callback<UserHoursAvailables>{
            override fun onResponse(
                call: Call<UserHoursAvailables>,
                response: Response<UserHoursAvailables>
            ) {
                if (response.body()!!.hoursAvailables >= _qhourSelected.value!!.toInt()){
                    status.value = true
                    message.value = "la operacion se realizo exitosamente"
                }else{
                    message.value =
                        "El codigo del segundo solo le queda ${response.body()!!.hoursAvailables} para reservar"
                }
            }

            override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {
                Log.i("RESERVAR: ","Hubo un error al reservar")
            }


        })

    }
}