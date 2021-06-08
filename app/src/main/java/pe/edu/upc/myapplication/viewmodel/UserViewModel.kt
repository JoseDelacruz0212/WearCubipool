package pe.edu.upc.myapplication.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.upc.myapplication.data.remote.ApiClient

import pe.edu.upc.myapplication.data.remote.user.UserRequest
import pe.edu.upc.myapplication.data.remote.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){

    var message = MutableLiveData<String>()

    var isCorrect = MutableLiveData<Boolean>()

    fun register(code:String,name:String,lastName:String,password:String){
        val register = UserRequest(code,name,lastName,password)
        val userResponse = ApiClient.build()?.postUser(register)
        userResponse?.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    isCorrect.value = true

                }else{
                    when(response.code()){
                        400 -> {
                            message.value = "Datos invalidos"
                        }
                        409 -> {
                            message.value = "usuario ya registrado"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                message.value = "GGAAAAAAA"
            }


        })
    }
}