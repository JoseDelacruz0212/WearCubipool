package pe.edu.upc.myapplication.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.data.remote.reservation.UserReservationsAvailables
import pe.edu.upc.myapplication.databinding.FragmentReservationsAvailablesBinding
import pe.edu.upc.myapplication.viewmodel.ReservationViewModel

class ReservationAvailablesFragment: Fragment(),MyCubiclesAdapter.CubicleItemListener {

    private var _binding:FragmentReservationsAvailablesBinding? = null
    private val binding get() = _binding!!
    private val viewModel:ReservationViewModel by viewModels()

    private lateinit var adapter: MyCubiclesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReservationsAvailablesBinding.inflate(inflater,container,false)

        setupView()
        setupRecyclerView()
        setupObservers()

        return binding.root
    }

    private fun setupView() {

        val sharedPreferences = activity?.getSharedPreferences("db_local",0)
        viewModel.code.value = sharedPreferences?.getString("code","u202120211")

        viewModel.getQuantityReservationsAvailable()

    }

    private fun setupObservers(){
        viewModel.myCubiclesAvailables.observe(viewLifecycleOwner,{ cubicles->
            if (cubicles != null){
                adapter.setItems(cubicles as ArrayList<UserReservationsAvailables>)
                Log.i("CUBICLESMy: ", cubicles[0].name)
            }else{
                adapter.setItems(ArrayList())
                Log.i("CUBICLESMy: ", "NO HAY DATOS")
            }

        })
    }

    private fun setupRecyclerView(){
        adapter = MyCubiclesAdapter(binding.root.context,this)
        binding.rvReservationsAvailables.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvReservationsAvailables.adapter = adapter
    }

    override fun onClickedCubicle(userReservationAvailables: UserReservationsAvailables) {
        //Toast.makeText(binding.root.context,cubicle.name,Toast.LENGTH_SHORT).show()

        val action = ReservationAvailablesFragmentDirections.actionReservationAvailablesFragmentToReservationDetailFragment(userReservationAvailables.id)
        NavHostFragment.findNavController(this).navigate(action)
    }


}