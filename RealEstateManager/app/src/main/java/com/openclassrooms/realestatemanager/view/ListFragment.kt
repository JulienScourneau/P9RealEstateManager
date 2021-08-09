package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import com.openclassrooms.realestatemanager.utils.onQueryTextChanger
import com.openclassrooms.realestatemanager.view.adapter.EstateAdapter
import com.openclassrooms.realestatemanager.viewmodel.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list_estate), EstateAdapter.OnItemClickListener {

    private lateinit var binding: FragmentListEstateBinding
    private val viewModel: EstateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentListEstateBinding.bind(view)

        val estateAdapter = EstateAdapter(this)

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

        viewModel.allEstate.observe(viewLifecycleOwner) {
            estateAdapter.submitList(it)
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddResult(result, "Estate Added")
        }
        
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.estateEvent.collect { event ->
                when (event) {
                    is EstateViewModel.EstateEvent.NavigateToAddEstateScreen -> {
                        val action =
                            ListFragmentDirections.actionListFragmentToAddEditEstateFragment(null)
                        findNavController().navigate(action)
                    }
                    is EstateViewModel.EstateEvent.NavigateToDetailsScreen -> {
                        val action =
                            ListFragmentDirections.actionListFragmentToDetailsFragment(event.id)
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
        Log.d("OptionsMenu", "createOptionsMenu")

        val searchItem = menu.findItem(R.id.action_search_estate)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanger {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search_estate -> {
                Toast.makeText(requireContext(), "search click", Toast.LENGTH_SHORT).show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(estate: EstateWithPhoto) {
        viewModel.onEstateSelected(estate.estate.id.toLong())
    }
}