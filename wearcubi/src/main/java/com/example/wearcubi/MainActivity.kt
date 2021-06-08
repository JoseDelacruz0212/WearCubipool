package com.example.wearcubi

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.wearcubi.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun GoReservetion(view: View){
        val intent=Intent(this,CurvedActivity::class.java)
        startActivity(intent)
    }




}