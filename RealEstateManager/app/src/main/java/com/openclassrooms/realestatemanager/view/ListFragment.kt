package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEstateBinding
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.utils.TestList
import com.openclassrooms.realestatemanager.databinding.EstateListBinding
import com.openclassrooms.realestatemanager.view.adapter.EstateAdapter
import kotlin.math.log

class ListFragment : Fragment(), EstateAdapter.OnItemClickListener {

    private lateinit var binding: EstateListBinding
    private lateinit var activityBinding: ActivityMainBinding


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
        setUpRecyclerview()

    }

    private fun setUpRecyclerview() {
        binding.recyclerview.adapter = EstateAdapter(TestList.getTestList, this)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {

        if (activityBinding.fragmentContainerDetails == null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main, EstateFragment(), null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
            Log.d("onItemClick", "if condition:")
        } else {
            activityBinding.fragmentContainerDetails?.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container_details, EstateFragment(), null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
            Log.d("onItemClick", "else condition:")
        }

    }
}