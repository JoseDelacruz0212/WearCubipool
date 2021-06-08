package pe.edu.upc.myapplication.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import pe.edu.upc.myapplication.R
import pe.edu.upc.myapplication.data.entities.CubicleF
import pe.edu.upc.myapplication.databinding.FragmentSearchCubicleBinding
import pe.edu.upc.myapplication.util.ReservationUtilities
import pe.edu.upc.myapplication.viewmodel.SearchViewModel

class SearchCubicleFragment : Fragment() {

    private var _binding: FragmentSearchCubicleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    lateinit var hoursAvailableAdapter: ArrayAdapter<String>;
    lateinit var dayAvailableAdapter: ArrayAdapter<String>;
    lateinit var qhoursAvailableAdapter: ArrayAdapter<String>;

    var hoursAvailable: MutableList<String> = ArrayList()
    var hourSelected = ""

    var dayAvailable: MutableList<String> = ArrayList()
    var daySelected = "Hoy"

    var quantityHoursAvailables: MutableList<String> = ArrayList()
    var qHoursSelected = ""





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchCubicleBinding.inflate(inflater, container, false)

        val sharedPreferences = activity?.getSharedPreferences("db_local",0)
        val code = sharedPreferences?.getString("code","u202120211")!!

        ////////// SET DAYS ////////
        viewModel.findHoursAvailableToday(code)
        viewModel.daysAvailables.observe(viewLifecycleOwner, { days ->
            dayAvailable = days
            dayAvailableAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item, dayAvailable
                )

            initDayAdapter(dayAvailableAdapter)
        })
        ///////////////////////////

        ////////// SET HOURS ////////////

        //viewModel.setHours()
        viewModel.hAvailable.observe(viewLifecycleOwner, { hAvailable ->
            Log.i("CON FE: ", "ENTRO")
            if (hAvailable != null) {
                hoursAvailable = hAvailable
                hoursAvailableAdapter =
                    ArrayAdapter(
                        requireContext(),
                        R.layout.support_simple_spinner_dropdown_item, hoursAvailable
                    )
                initHoursAvailablesAdapter(hoursAvailableAdapter)
            } else {
                Log.i("SET HOURS", "No hay DATOS")
            }
        })
        ///////////////////////////

        ////////// SET QUANTITY HOURS ////////////

        viewModel.hours.observe(viewLifecycleOwner, { qHours ->
            quantityHoursAvailables = qHours
            qhoursAvailableAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,quantityHoursAvailables
                )
            initQuantityHoursAdapter(qhoursAvailableAdapter)
        })
        ///////////////////////////

        viewModel.message.observe(viewLifecycleOwner,{msg ->
            validateTransition(msg)
        })

        binding.searchCubicleBtn.setOnClickListener {
            if(code == binding.etSecondCode.text.toString())
                validateTransition("Ingrese un codigo distinto al suyo")
            else
                viewModel.searchCubiclesAvailable(binding.etSecondCode.text.toString())
        }

        viewModel.status.observe(viewLifecycleOwner,{correct->
            Log.i("IsCorrectSearchCubicle: ",correct.toString())
            val cubicleF = CubicleF(code,hourSelected,qHoursSelected,daySelected,binding.etSecondCode.text.toString())
            val action = SearchCubicleFragmentDirections.actionSearchCubicleFragmentToCubicleAvailablesFragment(cubicleF)
            NavHostFragment.findNavController(this).navigate(action)
        })

        return binding.root
    }

    private fun initHoursAvailablesAdapter(hoursAvailableAdapter: ArrayAdapter<String>) {
        Log.i("SEARCH: ", "Entrando a setear las horas")

        binding.spinHoursAvailable.adapter = hoursAvailableAdapter

        if (viewModel._hourSelected.value != null) {
            val vmSpinnerPosition = hoursAvailableAdapter.getPosition(viewModel._hourSelected.value)
            hourSelected = hoursAvailable[vmSpinnerPosition]
            binding.spinHoursAvailable.setSelection(vmSpinnerPosition)
        }else{
            if (viewModel.hAvailable.value != null){
                hourSelected = hoursAvailable[0]
                Log.i("WPH: ", hourSelected)
            }
        }

        binding.spinHoursAvailable.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.i("SEARCH: ", "Selected")

                    hourSelected = hoursAvailable[position]
                    viewModel.startHour(hourSelected)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i("SEARCH: ", "Nothing  Selected")
                }

            }
    }

    private fun initDayAdapter(dayAvailableAdapter: ArrayAdapter<String>) {
        binding.spinDay.adapter = dayAvailableAdapter

        if (viewModel._daySelected.value != null) {
            val vmSpinnerPosition = dayAvailableAdapter.getPosition(viewModel._daySelected.value)
            daySelected = dayAvailable[vmSpinnerPosition]
            binding.spinDay.setSelection(vmSpinnerPosition)
        }

        binding.spinDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                daySelected = dayAvailable[position]
                viewModel.startDay(daySelected)

                viewModel._qhourSelected.value = null
                viewModel.setQuantityHours(daySelected)

                viewModel.hAvailable.value = null
                viewModel._hourSelected.value = null
                viewModel.setHours(daySelected)

                Log.i("WPD: ", daySelected)
                
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun initQuantityHoursAdapter(hoursAdapter: ArrayAdapter<String>){
        binding.spinHours.adapter = hoursAdapter

        if (viewModel._qhourSelected.value != null){
            val vmSpinnerPosition = qhoursAvailableAdapter.getPosition(viewModel._qhourSelected.value)
            qHoursSelected = quantityHoursAvailables[vmSpinnerPosition]
            binding.spinHours.setSelection(vmSpinnerPosition)
        }else{
            if (viewModel.hours.value != null){
                qHoursSelected = quantityHoursAvailables[0]
                Log.i("WPQ: ", qHoursSelected)
            }
        }

        binding.spinHours.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                qHoursSelected = quantityHoursAvailables[position]
                viewModel.startQuantityHours(qHoursSelected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")

            }

        }
    }

    private fun validateTransition(message:String) {
        Toast.makeText(this.context,message, Toast.LENGTH_LONG).show()
    }

    /*override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }*/
}