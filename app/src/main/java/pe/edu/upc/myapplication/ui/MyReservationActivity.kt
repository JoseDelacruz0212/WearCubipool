package pe.edu.upc.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.myapplication.databinding.ActivityMyReservationBinding

class MyReservationActivity : AppCompatActivity() {

    lateinit var binding:ActivityMyReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyReservationBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}