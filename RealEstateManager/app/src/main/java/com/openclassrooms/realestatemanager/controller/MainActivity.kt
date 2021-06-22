package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.view.MediaAdapter
import com.openclassrooms.realestatemanager.view.EstateAdapter
import com.openclassrooms.realestatemanager.models.PropertyItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.ListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.property_details.*


class MainActivity : AppCompatActivity() {

    private val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayListFragment(ListFragment())
        initView()

    }

    private fun initView() {
        setSupportActionBar(toolbar)
    }

    private fun displayListFragment(fragment: ListFragment) {
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }

}