package pe.edu.upc.myapplication.ui.reservation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pe.edu.upc.myapplication.data.entities.ParticipantsReservation
import pe.edu.upc.myapplication.databinding.PrototypeParticipantReservationBinding

class ParticipantReservationAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<ParticipantReservationAdapter.ParticipantPrototype>() {

    private val items: MutableList<ParticipantsReservation> = ArrayList()

    fun setItems(items: ArrayList<ParticipantsReservation>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantPrototype {
        val binding = PrototypeParticipantReservationBinding.inflate(LayoutInflater.from(context),parent,false)

        return ParticipantPrototype(binding)
    }

    override fun onBindViewHolder(prototype: ParticipantPrototype, position: Int) {
        prototype.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ParticipantPrototype(
        private val binding: PrototypeParticipantReservationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var participant: ParticipantsReservation

        fun bind(participant: ParticipantsReservation) {
            this.participant = participant
            binding.tvParticipantName.text = participant.name
            binding.tvParticipantCode.text = participant.code
            Glide.with(context).load(
                "https://intranet.upc.edu.pe/programas/Imagen/Fotos/Upc/0540${
                    participant.code.removeRange(
                        0,
                        1
                    )
                }.jpg"
            )
                .error(binding.imgParticipant.drawable)
                .fallback(binding.imgParticipant.drawable)
                .transform(CenterCrop(), RoundedCorners(25))
                .into(binding.imgParticipant)
        }

    }

}