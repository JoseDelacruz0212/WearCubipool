package com.example.wearcubi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.wearcubi.databinding.SearchBinding
import com.google.android.gms.wearable.DataClient

class SearchActivity:Activity() {
    private lateinit var binding: SearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}