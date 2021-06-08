package com.example.wearcubi

import android.app.Activity
import android.os.Bundle
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.wearcubi.databinding.VistaReservaBinding
class CurvedActivity :Activity(){
    private lateinit var binding: VistaReservaBinding
    private var menuItems = ArrayList<ReservaItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMenuItems()

        binding = VistaReservaBinding.inflate(layoutInflater)

        binding.wrLayout.apply {
            isEdgeItemsCenteringEnabled = true

            adapter = ReserveAdapter(menuItems, this@CurvedActivity)
            layoutManager = WearableLinearLayoutManager(this@CurvedActivity)

        }
        setContentView(binding.wrLayout)

    }
    private fun loadMenuItems() {
        menuItems.add(ReservaItem(" Cubiculo 12", R.drawable.ic_cubicle))
        menuItems.add(ReservaItem("Día:Hoy->Horarío: 14:00-16:00", android.R.drawable.ic_menu_my_calendar))
        menuItems.add(ReservaItem("Cantidad Maxíma : 6 personas", R.drawable.ic_teamwork))

    }
}