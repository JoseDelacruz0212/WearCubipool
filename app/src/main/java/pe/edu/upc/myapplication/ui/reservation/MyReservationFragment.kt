package pe.edu.upc.myapplication.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.WearableListenerService
import pe.edu.upc.myapplication.data.remote.reservation.UserReservationsAvailables
import pe.edu.upc.myapplication.databinding.FragmentMyReservationBinding
import pe.edu.upc.myapplication.viewmodel.ReservationViewModel


class MyReservationFragment: Fragment(){


    private var _binding: FragmentMyReservationBinding? = null
    private val binding get() = _binding!!
    private val viewModel:ReservationViewModel by viewModels()


    private var qReservation = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyReservationBinding.inflate(inflater,container,false)

        val sharedPreferences = activity?.getSharedPreferences("db_local",0)

        viewModel.code.value = sharedPreferences?.getString("code","u202120211")

        viewModel.getQuantityReservationsAvailable()
        setupObservers()

        return  binding.root

    }


    private fun setupObservers() {
        viewModel.qReservation.observe(viewLifecycleOwner,{ quantityReservation ->
            qReservation = quantityReservation
            getQuantityReservationAvailable()
        })
    }

    private fun getQuantityReservationAvailable() {


        binding.ivMyReservation.visibility = View.VISIBLE
        binding.etNumeroReservas.visibility = View.VISIBLE
        binding.loadingMyReservation.visibility = View.GONE
        binding.tvLoadingTextHomeFragment.visibility = View.GONE

        if(qReservation > 0){
            binding.btnViewReservations.visibility = View.VISIBLE
            binding.etNumeroReservas.text = "Actualmente tienes reservas"

            binding.btnViewReservations.setOnClickListener {
                navigateToReservationAvailables()
            }
        }


    }

    private fun navigateToReservationAvailables(){
        val action = MyReservationFragmentDirections.actionMyReservationFragment2ToMyReservationNavGraph()
        NavHostFragment.findNavController(this).navigate(action)
    }

}