package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSimulatorBinding
import kotlin.math.pow

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

            amountBorrowedEditTest.addTextChangedListener {
                if (it.toString().isNotBlank()) {
                    amount = it.toString().toInt()
                    displayResult(duration, amount, bring, rate)
                } else {
                    amount = 0
                }
            }

            bringEditText.addTextChangedListener {
                if (it.toString().isNotBlank()) {
                    bring = it.toString().toInt()
                    displayResult(duration, amount, bring, rate)
                } else {
                    bring = 0
                    displayResult(duration, amount, bring, rate)
                }
            }

            rateEditText.addTextChangedListener {
                if (it.toString().isNotBlank()) {
                    rate = it.toString().toDouble()
                    displayResult(duration, amount, bring, rate)
                } else {
                    rate = 0.0
                }
            }

            durationEditText.addTextChangedListener {
                if (it.toString().isNotBlank()) {
                    duration = it.toString().toInt()
                    displayResult(duration, amount, bring, rate)
                } else {
                    duration = 0
                }
            }

        }
    }

    private fun displayResult(duration: Int, amount: Int, bring: Int, rate: Double) {
        val monthlyRate = (1 + (rate / 100)).pow( 0.0833) - 1
        val totalAmount = ((amount - bring) * monthlyRate * ((1 + monthlyRate).pow(duration * 12)))
        val division = ((1 + monthlyRate).pow(duration * 12)) - 1
        val monthlyPayment = totalAmount / division
        //var monthlyPayment = ((amount - bring) * monthlyRate * ((1 + monthlyRate) * (duration * 12))) / ((1 + monthlyRate) * (duration * 12)) - 1
        binding.resultText.text = "Mensualité ${monthlyPayment.toInt()} par mois"
    }
}