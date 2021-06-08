package pe.edu.upc.myapplication.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.upc.myapplication.data.entities.Offer
import pe.edu.upc.myapplication.databinding.FragmentReservationDetailBinding
import pe.edu.upc.myapplication.viewmodel.ReservationViewModel


class ReservationDetailFragment : Fragment(){

    private var _binding:FragmentReservationDetailBinding? =null
    private val binding get() = _binding!!
    private val args: ReservationDetailFragmentArgs by navArgs()
    private val viewModel: ReservationViewModel by viewModels()

    private var reservationId = 0
    private val code = "u202120211"


    private lateinit var adapter: ParticipantReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReservationDetailBinding.inflate(inflater,container,false)

        setupView()
        setupRecyclerView()
        setupObservers()

        onClickButtons()

        binding.llOffer.visibility = View.GONE
        binding.llNoOffer.visibility = View.GONE
        binding.llOfferAppleTV.visibility = View.GONE
        binding.llOfferBoard.visibility = View.GONE
        binding.llActivar.visibility = View.GONE
        binding.tvNoactivate.visibility = View.GONE

        return binding.root
    }

    private fun setupRecyclerView(){
        adapter = ParticipantReservationAdapter(binding.root.context)
        binding.rvParticipantsReservations.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvParticipantsReservations.adapter = adapter
    }

    private fun setupView() {
        val id = args.idCubicle
        reservationId = id
        viewModel.idReservation.value = reservationId

        val sharedPreferences = activity?.getSharedPreferences("db_local",0)
        viewModel.code.value = sharedPreferences?.getString("code","u202120211")


        getReservation()
    }

    private fun getReservation() {
        viewModel.getReservation()
    }

    private fun setupObservers(){
        viewModel.myCubicleDetail.observe(viewLifecycleOwner,{ rDetail->
            if (rDetail != null){
                adapter.setItems(rDetail.participants)
                binding.tvRdCubiclename.text = rDetail.cubicleName
                binding.tvRdStartime.text = rDetail.hourInit
                binding.tvRdEndtime.text = rDetail.hourEnd
                binding.tvRdTheme.text = rDetail.theme

                if (rDetail.offer.size>0 && rDetail.status == "Activado" && rDetail.role == "Admin"){
                    showOffer(rDetail.offer[0])
                }
                else if (rDetail.offer.size<1 && rDetail.status == "Activado" && rDetail.role == "Admin"){
                    binding.llNoOffer.visibility = View.VISIBLE
                }
                else if (rDetail.status == "PorActivar" || rDetail.activate == "false"){
                    binding.llPorActivar.visibility = View.VISIBLE
                    binding.llActivar.visibility = View.VISIBLE
                }
                else if (rDetail.activate == "true"){
                    binding.llActivar.visibility = View.GONE
                }
                else if (rDetail.status == "Reservado"){
                    binding.tvNoactivate.visibility = View.VISIBLE
                }
            }

        })
        viewModel.isActivatedReservation.observe(viewLifecycleOwner,{ isActivated ->
            if(isActivated == true){
                binding.llPorActivar.visibility = View.GONE
            }
        })

    }

    private fun showOffer(offer:Offer) {
        binding.llOffer.visibility = View.VISIBLE

        if(offer.apple){
            binding.llOfferAppleTV.visibility = View.VISIBLE
        }
        if (offer.board){
            binding.llOfferBoard.visibility = View.VISIBLE
        }
        binding.tvRdAsientosOffer.text = offer.sitios.toString() + "asientos"
    }

    private fun onClickButtons(){
        binding.btnActivateReservation.setOnClickListener {
            viewModel.activateReservation()
            /*if (viewModel.isActivatedReservation.value == true){
                binding.llPorActivar.visibility = View.GONE
            }*/
        }

        binding.btnSharedCubicleActivity.setOnClickListener {

        }

        binding.btnEditOffer.setOnClickListener {

        }

    }

}