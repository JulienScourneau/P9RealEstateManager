package com.openclassrooms.realestatemanager.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.view.ListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    private lateinit var listFragment: ListFragment

    //private lateinit var addEditEstateFragment: AddEditEstateFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        //addEditEstateFragment = AddEditEstateFragment()
        binding.floatingActionButton.setOnClickListener {

            val intent = Intent(this, AddEditEstate::class.java)
            startActivity(intent)
            //if (!addEditEstateFragment.isVisible)
            //    if (binding.fragmentContainerDetails == null)
            //        fm.beginTransaction()
            //            .replace(R.id.fragment_container_main, addEditEstateFragment)
            //            .setReorderingAllowed(true)
            //            .addToBackStack(null)
            //            .commit()
            //Log.d("visibility", "fragment isVisible: " + addEditEstateFragment.isVisible)
        }
        displayListFragment()
    }

    private fun displayListFragment() {

        listFragment = ListFragment()
        fm.beginTransaction()
            .replace(R.id.fragment_container_main, listFragment)
            .setReorderingAllowed(true)
            .commit()

    }
}