package com.example.wearcubi

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.wearcubi.databinding.VistaReservaBinding
class ReservaActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_reserva)
    }
}