package pe.edu.upc.myapplication.data.remote

import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.data.entities.ReservationDetail
import pe.edu.upc.myapplication.data.remote.auth.AuthRequest
import pe.edu.upc.myapplication.data.remote.auth.AuthResponse
import pe.edu.upc.myapplication.data.remote.reservation.ActivateReservation
import pe.edu.upc.myapplication.data.remote.reservation.ReservationRequest
import pe.edu.upc.myapplication.data.remote.reservation.UserReservationsAvailables
import pe.edu.upc.myapplication.data.remote.user.UserHoursAvailables
import pe.edu.upc.myapplication.data.remote.user.UserRequest
import pe.edu.upc.myapplication.data.remote.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //USER
    @POST("auth")
    fun authenticate(@Body authRequest: AuthRequest): Call<AuthResponse>

    @POST("users")
    fun postUser(@Body userRequest: UserRequest): Call<UserResponse>

    @GET("users/{id}/hoursAvailable")
    fun getHoursAvailablesByDay(@Path("id")id:String, @Query("date") date:String)
            :Call<UserHoursAvailables>

    @GET("users/{id}")
    fun findById(@Path("id")id:String):Call<UserResponse>

    @GET("cubicles")
    fun findAllAvailableCubicles(@Query("date") date:String,
                                 @Query("startTime") startTime:String,
                                 @Query("hours") hours:String)
    : Call<ArrayList<Cubicle>>

    @POST("reservation")
    fun submitReservation(@Body reservationRequest: ReservationRequest): Call<ReservationRequest>



    @GET("users/{id}/reservations?availables=true")
    fun getReservationsAvailables(@Path("id") id:String)
            :Call<ArrayList<UserReservationsAvailables>>

    @GET("reservation/{id}/{code}")
    fun findReservationById(@Path("id") id: Int, @Path("code") code: String): Call<ReservationDetail>

    @PUT("reservation/activate")
    fun activateCubicle(@Body activateReservation: ActivateReservation): Call<ActivateReservation>

}