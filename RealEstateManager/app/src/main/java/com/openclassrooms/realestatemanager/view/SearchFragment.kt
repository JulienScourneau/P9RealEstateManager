package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchEstateBinding

class SearchFragment : Fragment(R.layout.fragment_search_estate) {

    private lateinit var binding: FragmentSearchEstateBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSearchEstateBinding.bind(view)

        binding.apply {
            setupExpandView(arrowButtonPrice, priceLayout, baseLayoutPrice)
        }
    }

    private fun setupExpandView(
        btn: ImageView,
        expandLayout: LinearLayout,
        baseLayout: LinearLayout
    ) {
        btn.setOnClickListener {
            if (expandLayout.visibility == View.VISIBLE) {
                expandLayout.visibility = View.GONE
                TransitionManager.beginDelayedTransition(baseLayout, AutoTransition())
                btn.setImageResource(R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(baseLayout, AutoTransition())
                expandLayout.visibility = View.VISIBLE
                btn.setImageResource(R.drawable.ic_baseline_expand_less_24)
            }
        }
    }
}