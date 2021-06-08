package pe.edu.upc.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.upc.myapplication.data.remote.ApiClient
import pe.edu.upc.myapplication.data.remote.auth.AuthRequest
import pe.edu.upc.myapplication.data.remote.auth.AuthResponse
import pe.edu.upc.myapplication.data.remote.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel: ViewModel() {

    var message = MutableLiveData<String>()

    var isCorrect = MutableLiveData<Boolean>()

    var user = MutableLiveData<AuthResponse>()

    fun auth(code:String,password:String){
        val login = AuthRequest(code,password)
       val authResponse = ApiClient.build()?.authenticate(login)

       authResponse?.enqueue(object: Callback<AuthResponse> {
           override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
               if(response.isSuccessful){

                   message.value = "Ingreso correctamente"
                   isCorrect.value = true
                   user.value = response.body()
               }else{
                   when(response.code()){
                       404 -> {
                           message.value = "Credenciales Inválidas"
                       }

                       400 -> {
                           message.value = "Datos incompletos"
                       }
                   }
               }
           }

           override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
               //Log.i("PruebaLogin","Algo salio mal")
               //message = "Algo salio mal"
               message.value = "Algo salio mal"

           }


       })

   }

}
