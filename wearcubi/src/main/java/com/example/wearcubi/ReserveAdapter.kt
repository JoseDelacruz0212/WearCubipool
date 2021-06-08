package com.example.wearcubi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wearcubi.databinding.ReservaItemBinding
class ReserveAdapter(private val menuItems: ArrayList<ReservaItem>, val context: Context):
RecyclerView.Adapter<ReserveAdapter.Prototype>(){
    inner class Prototype(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindTo(menuItem: ReservaItem) {

            itemView.findViewById<ImageView>(R.id.menu_icon).apply {
                setImageResource(menuItem.image)
            }
            itemView.findViewById<TextView>(R.id.menu_text).apply {
                text = menuItem.text
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {

        val binding = ReservaItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return Prototype(binding.root)
    }
    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bindTo(menuItems[position])
    }
}