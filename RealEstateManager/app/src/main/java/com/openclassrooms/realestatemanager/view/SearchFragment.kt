package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding

class SearchFragment : Fragment(R.layout.fragment_search_estate) {

    private lateinit var binding: FragmentSearchEstateBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchEstateBinding.bind(view)
        setupListener()

    }

    private fun setupListener() {
        binding.apply {
            arrowButton.setOnClickListener {
                if (hiddenView.visibility == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
                    hiddenView.visibility = View.GONE
                    arrowButton.setImageResource(R.drawable.ic_baseline_expand_more_24)
                } else {
                    TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
                    hiddenView.visibility = View.VISIBLE
                    arrowButton.setImageResource(R.drawable.ic_baseline_expand_less_24)
                }
            }
        }
    }
}