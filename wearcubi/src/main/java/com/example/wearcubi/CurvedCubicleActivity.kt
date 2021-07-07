package com.example.wearcubi
import android.app.Activity
import android.os.Bundle
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.wearcubi.databinding.VistaCubiculosBinding
class CurvedCubicleActivity :Activity(){
    private lateinit var binding: VistaCubiculosBinding
    private var menuItems = ArrayList<CubicleItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMenuItems()

        binding = VistaCubiculosBinding.inflate(layoutInflater)

        binding.wrLayoutCubi.apply {
            isEdgeItemsCenteringEnabled = true

            adapter = CubicleAdapter(menuItems , this@CurvedCubicleActivity)
            layoutManager = WearableLinearLayoutManager(this@CurvedCubicleActivity)

        }
        setContentView(binding.wrLayoutCubi)

    }
    private fun loadMenuItems() {
        menuItems.add(CubicleItem(" Cubiculo 1", R.drawable.ic_available))
        menuItems.add(CubicleItem(" Cubiculo 2", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 3", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 4", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 5", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 6", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 7", R.drawable.ic_available))
        menuItems.add(CubicleItem(" Cubiculo 8", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 9", R.drawable.ic_available))
        menuItems.add(CubicleItem(" Cubiculo 10", R.drawable.ic_available))
        menuItems.add(CubicleItem(" Cubiculo 11", R.drawable.ic_reserved))
        menuItems.add(CubicleItem(" Cubiculo 12", R.drawable.ic_reserved))
    }
}