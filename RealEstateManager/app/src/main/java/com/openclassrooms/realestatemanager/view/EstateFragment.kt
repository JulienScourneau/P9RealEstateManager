package com.openclassrooms.realestatemanager.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.AddEditEstate
import com.openclassrooms.realestatemanager.databinding.EstateDetailsBinding
import com.openclassrooms.realestatemanager.view.adapter.MediaAdapter
import com.openclassrooms.realestatemanager.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstateFragment : Fragment(R.layout.estate_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter
    private var images: ArrayList<Int> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val binding = EstateDetailsBinding.bind(view)

        val args = this.arguments
        val id = args?.get("estate_id")

        viewModel.getEstateById(id as Long).observe(viewLifecycleOwner) { estateWithPhoto ->

        }

        mediaAdapter = MediaAdapter(requireContext(), images)
        setupViewPager()
        binding.viewpager.adapter = mediaAdapter
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_estate -> {
                val intent = Intent(requireContext(), AddEditEstate::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager() {

        images.add(R.drawable.house)
        images.add(R.drawable.kitchen)
        images.add(R.drawable.bathroom)
        images.add(R.drawable.bedroom)
        images.add(R.drawable.living_room)
    }

}