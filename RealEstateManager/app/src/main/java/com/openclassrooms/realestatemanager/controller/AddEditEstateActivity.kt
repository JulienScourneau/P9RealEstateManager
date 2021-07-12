package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding

class AddEditEstateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEstateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.categorySpinner
        ArrayAdapter.createFromResource(
            this, R.array.category_spinner, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
    }
}