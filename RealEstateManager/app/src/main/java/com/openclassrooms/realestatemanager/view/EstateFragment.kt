package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.EstateDetailsBinding
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter

class EstateFragment : Fragment() {

    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()
    private var binding: EstateDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EstateDetailsBinding.inflate(inflater, container, false)
        return binding!!.root

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

        binding?.viewpager?.adapter = mediaAdapter
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}