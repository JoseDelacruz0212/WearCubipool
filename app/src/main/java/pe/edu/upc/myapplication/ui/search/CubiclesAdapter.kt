package pe.edu.upc.myapplication.ui.search

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.myapplication.R
import pe.edu.upc.myapplication.data.entities.Cubicle
import pe.edu.upc.myapplication.databinding.PrototypeCubicleItemBinding

class CubiclesAdapter(
    val context: Context,
    private val listener: CubicleItemListener
    ):
RecyclerView.Adapter<CubiclesAdapter.CubiclePrototype>()
{
    private val items: MutableList<Cubicle> = ArrayList()

    interface CubicleItemListener{
        fun onClickedCubicle(cubicle:Cubicle)
    }

    fun setItems(items: ArrayList<Cubicle>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CubiclePrototype {
        val binding =
            PrototypeCubicleItemBinding.inflate(LayoutInflater.from(context), parent,false)

        return CubiclePrototype(binding,listener)
    }

    override fun onBindViewHolder(prototype: CubiclePrototype, position: Int) {
        prototype.bind(items[position])

        if (items[position].status){
            prototype.paintCubicleCard()
        }else{
            prototype.removePaint()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CubiclePrototype(
        private val binding: PrototypeCubicleItemBinding,
        private val listener: CubicleItemListener
    ): RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {
        private lateinit var cubicle: Cubicle

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(cubicle: Cubicle){
            this.cubicle = cubicle
            binding.cubicleName.text = this.cubicle.name
            binding.dateCubicle.text = this.cubicle.day
            binding.startTimeCubicle.text = this.cubicle.startTime
            binding.endTimeCubicle.text = this.cubicle.endTime
        }

        override fun onClick(v: View?) {
            listener.onClickedCubicle(cubicle)
            notifyDataSetChanged()
        }

        fun paintCubicleCard(){
            binding.root.setBackgroundResource(R.drawable.cubicle_item_selected)
            binding.cubicleName.setTextColor(Color.parseColor("#ffffff"))
            binding.dateCubicle.setTextColor(Color.parseColor("#ffffff"))
            binding.startTimeCubicle.setTextColor(Color.parseColor("#ffffff"))
            binding.endTimeCubicle.setTextColor(Color.parseColor("#ffffff"))
        }
        fun removePaint(){
            binding.root.setBackgroundResource(R.drawable.card_cubicle)
            binding.cubicleName.setTextColor(Color.parseColor("#000000"))
            binding.dateCubicle.setTextColor(Color.parseColor("#000000"))
            binding.startTimeCubicle.setTextColor(Color.parseColor("#000000"))
            binding.endTimeCubicle.setTextColor(Color.parseColor("#000000"))
        }

    }

}