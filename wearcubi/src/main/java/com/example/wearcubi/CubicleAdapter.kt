package com.example.wearcubi
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wearcubi.databinding.CubicleItemBinding
class CubicleAdapter(private val menuItems: ArrayList<CubicleItem>, val context: Context):
    RecyclerView.Adapter<CubicleAdapter.Prototype>(){
    inner class Prototype(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindTo(menuItem: CubicleItem) {

            itemView.findViewById<ImageView>(R.id.menu_icon).apply {
                setImageResource(menuItem.image)
            }
            itemView.findViewById<TextView>(R.id.menu_text).apply {
                text = menuItem.text
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {

        val binding = CubicleItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return Prototype(binding.root)
    }
    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bindTo(menuItems[position])
    }
}