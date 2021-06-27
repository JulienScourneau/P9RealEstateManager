package com.openclassrooms.realestatemanager.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding
import com.openclassrooms.realestatemanager.databinding.EstateListBinding

class EstateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}