package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.EstateDetailsBinding
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter

class EstateFragment : Fragment(R.layout.estate_details) {

    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()
    private lateinit var fragmentContainer: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = EstateDetailsBinding.bind(view)

        mediaAdapter = MediaAdapter(requireContext(), images)
        setupViewPager()
        binding.viewpager.adapter = mediaAdapter
    }

    private fun setupViewPager() {

        images.add(R.drawable.house)
        images.add(R.drawable.kitchen)
        images.add(R.drawable.bathroom)
        images.add(R.drawable.bedroom)
        images.add(R.drawable.living_room)


    }


}