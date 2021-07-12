package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding

class AddEditEstateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}