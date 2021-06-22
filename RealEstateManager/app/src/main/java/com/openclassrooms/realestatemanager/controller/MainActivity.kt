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


class MainActivity : AppCompatActivity(), EstateAdapter.OnItemClickListener {

    private lateinit var images: ArrayList<Int>
    private lateinit var mediaAdapter: MediaAdapter
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

    override fun onItemClick(position: Int) {
        setupViewPager()

        Toast.makeText(this, "Click: $position", Toast.LENGTH_SHORT).show()

    }

    private fun setupViewPager() {
        setContentView(R.layout.property_details)
        images = ArrayList()

        images.add(R.drawable.house)
        images.add(R.drawable.bleu)
        images.add(R.drawable.rouge)
        images.add(R.drawable.orange)
        images.add(R.drawable.vert)

        mediaAdapter = MediaAdapter(this, images)
        viewpager.adapter = mediaAdapter

    }

}