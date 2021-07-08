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
    private lateinit var binding: EstateDetailsBinding
    private lateinit var fragmentContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EstateDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        mediaAdapter = MediaAdapter(requireContext(), images)
        setupViewPager()

    }

    private fun setupViewPager() {

        images.add(R.drawable.house)
        images.add(R.drawable.kitchen)
        images.add(R.drawable.bathroom)
        images.add(R.drawable.bedroom)
        images.add(R.drawable.living_room)

        binding.viewpager.adapter = mediaAdapter
    }


}