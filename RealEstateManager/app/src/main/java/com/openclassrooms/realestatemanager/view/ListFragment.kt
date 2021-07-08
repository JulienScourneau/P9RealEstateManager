package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.databinding.EstateListBinding
import com.openclassrooms.realestatemanager.view.adapter.EstateAdapter
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.estate_list), EstateAdapter.OnItemClickListener {

    private lateinit var binding: EstateListBinding
    private lateinit var activityBinding: ActivityMainBinding
    private val viewModel: EstateViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EstateListBinding.inflate(inflater, container, false)
        activityBinding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val estateAdapter = EstateAdapter(this)

        binding.apply {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = estateAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.allEstate.observe(viewLifecycleOwner) {
            estateAdapter.submitList(it)
        }
    }

    override fun onItemClick(position: Int) {

        if (activityBinding.fragmentContainerDetails == null) {
            displayEstateFragment(R.id.fragment_container_main)
            Log.d("onItemClick", "if condition:")
        } else {
            activityBinding.fragmentContainerDetails?.visibility = View.VISIBLE
            displayEstateFragment(R.id.fragment_container_details)
            Log.d("onItemClick", "else condition:")
        }
    }

    private fun displayEstateFragment(container: Int) {
        parentFragmentManager.beginTransaction()
            .add(container, EstateFragment(), null)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}