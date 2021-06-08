package pe.edu.upc.myapplication.ui.search

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
import pe.edu.upc.myapplication.R
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.databinding.FragmentCubicleAvailablesBinding
import pe.edu.upc.myapplication.viewmodel.CubicleViewModel

class CubicleAvailablesFragment : Fragment(),CubiclesAdapter.CubicleItemListener {

    private var _binding:FragmentCubicleAvailablesBinding? = null
    private val binding get() = _binding!!
    private val args: CubicleAvailablesFragmentArgs by navArgs()
    private var hour:String = ""
    private var quantityHours:String=""
    private var date:String = ""
    private var secondCode = ""
    private var firstCode =""

    private lateinit var adapter: CubiclesAdapter
    private val viewModel: CubicleViewModel by viewModels()

    private  var lastCubicle= Cubicle(0,"","","","")
    private var isCubicleSelected = false
    private var firstTimeSelected = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCubicleAvailablesBinding.inflate(inflater,container,false)


        setupView()
        setupRecyclerView()
        if (viewModel._cubiclesAvailables.value == null) {
            viewModel.searchAllCubicle()
        }

        if (viewModel.firstTimeSelected.value != null) {
            firstTimeSelected = viewModel.firstTimeSelected.value!!
        }

        if (viewModel.isCubicleSelected.value != null) {
            isCubicleSelected = viewModel.isCubicleSelected.value!!
        }

        if (viewModel.lastCubicle.value != null) {
            lastCubicle = viewModel.lastCubicle.value!!
        }

        binding.btnSubmitReservation.setOnClickListener {
            viewModel.startDateSubmit()
            viewModel.reservationSubmit(firstCode,secondCode,lastCubicle.id)
        }


        setupObservers()

        Log.i("VMC1: ", firstTimeSelected.toString())
        Log.i("VMC2: ", isCubicleSelected.toString())
        Log.i("VMC3: ", lastCubicle.name)


        return binding.root
    }

    private fun setupView(){
        val cubicleArg = args.cublicleF
        hour = cubicleArg.hour
        quantityHours = cubicleArg.qHours
        date = cubicleArg.date
        firstCode = cubicleArg.firstCode
        secondCode = cubicleArg.secondCode

        viewModel.startVariables(date,hour,quantityHours)
    }

    private fun setupRecyclerView(){
        adapter = CubiclesAdapter(binding.root.context,this)
        binding.listView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.listView.adapter = adapter
    }

    override fun onClickedCubicle(cubicle: Cubicle) {
        Log.i("CUBICLE: ", cubicle.name)
        cubicle.status =true

        if (firstTimeSelected){
            lastCubicle = cubicle
            firstTimeSelected = false

            viewModel.lastCubicle.value = lastCubicle
            viewModel.firstTimeSelected.value = firstTimeSelected
        }

        if(isCubicleSelected ) {

            if(lastCubicle.id != cubicle.id) {
                lastCubicle.status = false
                lastCubicle = cubicle

                viewModel.lastCubicle.value = lastCubicle


            }
        }
        isCubicleSelected = true
        viewModel.isCubicleSelected.value = isCubicleSelected

    }

    private fun setupObservers(){
        viewModel._cubiclesAvailables.observe(viewLifecycleOwner,{ cubicles ->

            if (cubicles!=null) {
                adapter.setItems(cubicles as ArrayList<Cubicle>)
                Log.i("CUBICLES: ", cubicles[0].name)
            }
            else {
                adapter.setItems(ArrayList())
                Log.i("CUBICLES: ", "NO HAY DATOS")
            }
        })

        viewModel.isCorrect.observe(viewLifecycleOwner,{correct ->
            val action = CubicleAvailablesFragmentDirections.actionCubicleAvailablesFragmentToReservationSuccessFragment()
            NavHostFragment.findNavController(this).navigate(action)
        })

        viewModel.message.observe(viewLifecycleOwner,{msg ->
            validateTransition(msg)
        })
    }

    private fun validateTransition(message:String) {
        Toast.makeText(this.context,message, Toast.LENGTH_LONG).show()
    }


}