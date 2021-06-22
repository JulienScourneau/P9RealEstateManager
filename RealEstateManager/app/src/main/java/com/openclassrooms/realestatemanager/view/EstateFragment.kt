package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.property_details.*

class EstateFragment : Fragment() {

    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.property_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mediaAdapter = MediaAdapter(requireContext(), images)
        setupViewPager()
    }

    private fun setupViewPager() {

        images.add(R.drawable.house)
        images.add(R.drawable.bleu)
        images.add(R.drawable.rouge)
        images.add(R.drawable.orange)
        images.add(R.drawable.vert)

        viewpager.adapter = mediaAdapter

    }

}