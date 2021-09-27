package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSimulatorBinding

class SimulatorFragment : Fragment(R.layout.fragment_simulator) {

    private lateinit var binding: FragmentSimulatorBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.toolbar_simulator)
        binding = FragmentSimulatorBinding.bind(view)
    }

}