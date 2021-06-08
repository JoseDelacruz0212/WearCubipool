package pe.edu.upc.myapplication.ui.search

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

import pe.edu.upc.myapplication.R
import pe.edu.upc.myapplication.databinding.FragmentReservationSucessBinding

class ReservationSuccessFragment: Fragment()
{

    private var _binding:FragmentReservationSucessBinding? = null
    private val binding get() = _binding!!

    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notificationID = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentReservationSucessBinding.inflate(inflater,container,false)

        ////////////////
        createNotification()
        val notification = NotificationCompat.Builder(binding.root.context,channelID).also {
            it.setSmallIcon(R.drawable.ic_logo)
            it.setContentTitle("Reserva Exitosa")
            it.setContentText("Se ha elaborado su reserva correctamente")
            it.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }.build()
        val notificationManager = NotificationManagerCompat.from(binding.root.context)
        //////////////


        binding.btnGotToHome.setOnClickListener {
            val action = ReservationSuccessFragmentDirections.actionReservationSuccessFragmentToHomeActivity2()
            NavHostFragment.findNavController(this).navigate(action)
            notificationManager.notify(notificationID,notification)
        }

        return binding.root
    }

    private fun createNotification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID,channelName,importance).apply {
                enableLights(true)
            }
            //val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val manager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }
}
