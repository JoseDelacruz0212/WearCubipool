package pe.edu.upc.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.edu.upc.myapplication.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}