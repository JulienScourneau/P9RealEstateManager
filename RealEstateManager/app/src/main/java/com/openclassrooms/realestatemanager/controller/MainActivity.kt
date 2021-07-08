package com.openclassrooms.realestatemanager.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.view.EstateFragment
import com.openclassrooms.realestatemanager.view.ListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    private lateinit var listFragment: ListFragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, EstateActivity::class.java)
            startActivity(intent)
        }
        displayListFragment()
    }

    private fun displayListFragment() {

        //binding.fragmentContainerDetails?.visibility = View.INVISIBLE
        listFragment = ListFragment()
        fm.beginTransaction()
            .replace(R.id.fragment_container_main, listFragment)
            .setReorderingAllowed(true)
            .commit()

    }
}