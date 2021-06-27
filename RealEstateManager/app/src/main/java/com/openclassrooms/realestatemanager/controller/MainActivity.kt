package com.openclassrooms.realestatemanager.controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.view.ListFragment


class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayListFragment(ListFragment())
        initView()

    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EstateActivity::class.java)
            startActivity(intent)
        })
    }

    private fun displayListFragment(fragment: ListFragment) {
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }

}