package pe.edu.upc.myapplication.util

import pe.edu.upc.myapplication.data.remote.ApiClient
import pe.edu.upc.myapplication.data.remote.user.UserHoursAvailables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationUtilities {

    var currentDateTime = LocalDateTime.now()
    var time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    var actualHour = time.toString().take(2).toInt()


    ////////////////////////////////////////////////////HOURS AVAILABLE
    fun hourValidation(hoursAvailable: MutableList<String>, date:String){
        if (date == "Hoy") {
            if (actualHour < 22) {
                if (actualHour <= 7) {
                    actualHour = 7
                }
                addListHoursAvailable(hoursAvailable)
            } else {
                setAllHoursAvailableForTomorrow(hoursAvailable)
            }
        }else if (date == "Mañana"){
            setAllHoursAvailableForTomorrow(hoursAvailable)
        }
    }

    private fun setAllHoursAvailableForTomorrow(hoursList: MutableList<String>) {
        for (i in 7..22){
            if(i<10){
                hoursList.add("0$i:00")
            }else{
                hoursList.add("$i:00")
            }
        }
    }

    private fun addListHoursAvailable(hoursList: MutableList<String>) {
        for (i in actualHour..22){
            if(i<10){
                hoursList.add("0$i:00")
            }else{
                hoursList.add("$i:00")
            }
        }
    }


    ///////////////////////////////////////////////////////////

    /*fun findHoursAvailableToday(code:String,dayAvailable: MutableList<String>){
        val  userFindHoursResponse = ApiClient.build()?.getHoursAvailablesByDay(code,"Hoy")

        userFindHoursResponse?.enqueue(object: Callback<UserHoursAvailables> {
            override fun onResponse(
                call: Call<UserHoursAvailables>,
                response: Response<UserHoursAvailables>
            ) {
                if(response.body()?.hoursAvailables != 0){
                    if(actualHour < 22){
                        dayAvailable.add("Hoy")
                    }
                }
                findHoursAvailableTomorrow(code,dayAvailable)
            }

            override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {

            }

        })
    }

    fun findHoursAvailableTomorrow(code:String,dayAvailable: MutableList<String>){
        val  userFindHoursTomorrowResponse = ApiClient.build()?.getHoursAvailablesByDay(code,"Mañana")

        userFindHoursTomorrowResponse?.enqueue(object: Callback<UserHoursAvailables>{
            override fun onResponse(
                call: Call<UserHoursAvailables>,
                response: Response<UserHoursAvailables>
            ) {
                if (response.body()?.hoursAvailables != 0){

                    dayAvailable.add("Mañana")

                }
            }

            override fun onFailure(call: Call<UserHoursAvailables>, t: Throwable) {

            }

        })

    }*/

    ////////////////////////////////////////////////////

}