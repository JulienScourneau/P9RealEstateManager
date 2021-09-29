package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
        setupUI()
    }

    private fun setupUI() {
        var duration = 2
        var amount = 0
        var bring = 0
        var rate = 0.0
        binding.apply {

            durationText.text = "$duration ans"
            if (duration != 1) {
                btnLeft.setOnClickListener {
                    duration--
                    updateDuration(duration)
                    //displayResult(duration, amount, bring, rate)
                }
            } else {
                btnLeft.isClickable = false
            }
            btnRight.setOnClickListener {
                duration++
                updateDuration(duration)
                //displayResult(duration, amount, bring, rate)
            }

            amountBorrowedEditTest.addTextChangedListener {
                amount = it.toString().toInt()
            }

            bringEditText.addTextChangedListener {
                bring = it.toString().toInt()
            }

            rateEditText.addTextChangedListener {
                rate = it.toString().toDouble()
            }
        }
    }

    private fun updateDuration(duration: Int) {
        binding.durationText.text = "$duration ans"
    }

}