package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.FragmentListEstateBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.view.adapter.EstateAdapter
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list_estate), EstateAdapter.OnItemClickListener {

    private lateinit var binding: FragmentListEstateBinding
    private val viewModel: EstateViewModel by viewModels()
    private var estateList: List<EstateWithPhoto> = ArrayList()
    private val estateAdapter = EstateAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.toolbar_estate)

        binding = FragmentListEstateBinding.bind(view)

        binding.apply {
            recyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = estateAdapter
                setHasFixedSize(true)
            }
            fabAddEstate.setOnClickListener {
                viewModel.onAddNewEstateClick()
            }
        }
        displayEstateList()

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddResult(result, "Estate Added")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.estateEvent.collect { event ->
                when (event) {

                    is EstateViewModel.EstateEvent.NavigateToAddEstateScreen -> {
                        (activity as AppCompatActivity)
                            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        val action =
                            ListFragmentDirections.actionListFragmentToAddEditEstateFragment(
                                null,
                                resources.getString(R.string.toolbar_add_estate)
                            )
                        findNavController().navigate(action)
                    }

                    is EstateViewModel.EstateEvent.NavigateToSearchScreen -> {
                        (activity as AppCompatActivity)
                            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        val action =
                            ListFragmentDirections.actionListFragmentToSearchFragment()
                        findNavController().navigate(action)
                    }

                    is EstateViewModel.EstateEvent.NavigateToMapScreen -> {
                        if (Utils.isInternetAvailable(requireContext())) {
                            (activity as AppCompatActivity)
                                .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                            val action =
                                ListFragmentDirections.actionListFragmentToMapsFragment()
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "La map nécessite une connexion Internet",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is EstateViewModel.EstateEvent.NavigateToDetailsScreen -> {
                        (activity as AppCompatActivity)
                            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        val action =
                            ListFragmentDirections.actionListFragmentToDetailsFragment(event.id)
                        findNavController().navigate(action)
                    }

                    is EstateViewModel.EstateEvent.NavigateToSimulatorScreen -> {
                        (activity as AppCompatActivity)
                            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                        val action =
                            ListFragmentDirections.actionListFragmentToSimulatorFragment()
                        findNavController().navigate(action)
                    }

                    is EstateViewModel.EstateEvent.ShowEstateAddedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search_estate -> {
                viewModel.onSearchEstateClick()
                true
            }
            R.id.action_maps -> {
                viewModel.onMapClick()
                true
            }
            android.R.id.home -> {
                viewModel.searchEstate = null
                (activity as AppCompatActivity)
                    .supportActionBar?.setDisplayHomeAsUpEnabled(false)
                displayEstateList()
                true
            }
            R.id.action_simulator -> {
                viewModel.onSimulatorClick()
                true
            }
            R.id.action_sort_euro -> {
                Toast.makeText(requireContext(), "Euro click", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_dollar -> {
                Toast.makeText(requireContext(), "Dollar click", Toast.LENGTH_SHORT).show()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(estate: EstateWithPhoto) {
        viewModel.onEstateSelected(estate.estate.id.toLong())
    }

    private fun displayEstateList() {
        if (viewModel.searchEstate != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            viewModel.getEstateBySearch(Utils.createRawQueryString(viewModel.searchEstate))
                .observe(viewLifecycleOwner) {
                    estateAdapter.submitList(it)
                }
        } else {
            viewModel.allEstate.observe(viewLifecycleOwner) {
                estateList = it
                estateAdapter.submitList(it)
            }
        }
    }

}