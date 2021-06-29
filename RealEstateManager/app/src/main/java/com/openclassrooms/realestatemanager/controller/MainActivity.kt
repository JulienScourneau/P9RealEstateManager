package com.openclassrooms.realestatemanager.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.view.EstateFragment
import com.openclassrooms.realestatemanager.view.ListFragment


class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    private lateinit var listFragment: ListFragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var estateFragment: EstateFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayListFragment()
        initView()
        displayDetailsFragment()

    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, EstateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayListFragment() {
        listFragment = ListFragment()
        fm.beginTransaction()
            .replace(R.id.fragment_container_main, listFragment)
            .setReorderingAllowed(true)

            .commit()

    }

    private fun displayDetailsFragment() {
        estateFragment = EstateFragment()
        fm.findFragmentById(R.id.fragment_container_details)

        if (binding.fragmentContainerDetails != null) {
            fm.beginTransaction().add(R.id.fragment_container_details, estateFragment).commit()
        }
    }

}