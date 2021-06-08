package pe.edu.upc.myapplication.ui.reservation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.data.remote.reservation.UserReservationsAvailables
import pe.edu.upc.myapplication.databinding.PrototypeReservationAvailablesBinding
import pe.edu.upc.myapplication.ui.search.CubiclesAdapter

class MyCubiclesAdapter(
    val context:Context,
    private val listener: MyCubiclesAdapter.CubicleItemListener):
RecyclerView.Adapter<MyCubiclesAdapter.MyCubiclesPrototype>(){

    private val items: MutableList<UserReservationsAvailables> = ArrayList()

    interface CubicleItemListener{
        fun onClickedCubicle(cubicle: UserReservationsAvailables)
    }

    fun setItems(items: ArrayList<UserReservationsAvailables>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCubiclesPrototype {
        val binding = PrototypeReservationAvailablesBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyCubiclesPrototype(binding,listener)
    }

    override fun onBindViewHolder(prototype: MyCubiclesPrototype, position: Int) {
        prototype.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class MyCubiclesPrototype(
        private val binding: PrototypeReservationAvailablesBinding,
        private val listner: CubicleItemListener
    ):RecyclerView.ViewHolder(binding.root),View.OnClickListener {

        private lateinit var cubicle:UserReservationsAvailables

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(cubicle: UserReservationsAvailables){
            this.cubicle = cubicle
            binding.tvReservationAvailableCubicleName.text = this.cubicle.name
            binding.tvReservationAvailableDay.text = this.cubicle.day
            binding.tvReservationAvailableStartTime.text = this.cubicle.startTime
            binding.tvReservationAvailableEndTime.text = this.cubicle.endTime

        }

        override fun onClick(v: View?) {
            listner.onClickedCubicle(cubicle)
        }

    }

}