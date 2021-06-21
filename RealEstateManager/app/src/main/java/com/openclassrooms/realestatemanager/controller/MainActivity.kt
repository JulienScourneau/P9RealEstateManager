package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.view.MediaAdapter
import com.openclassrooms.realestatemanager.view.PropertyAdapter
import com.openclassrooms.realestatemanager.models.PropertyItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.ListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.property_details.*


class MainActivity : AppCompatActivity(), PropertyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private val testList = generateDummyList(20)
    private val adapter = PropertyAdapter(testList, this)
    private lateinit var images: ArrayList<Int>
    private lateinit var mediaAdapter: MediaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun initView(){
        setSupportActionBar(toolbar)

        var listFragment:ListFragment

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, listFragment,"1").commit()

        recyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        setupViewPager()

        Toast.makeText(this, "Click: $position", Toast.LENGTH_SHORT).show()

    }

    private fun generateDummyList(size: Int): ArrayList<PropertyItem> {
        val list = ArrayList<PropertyItem>()

        for (i in 0 until size) {

            val drawable = R.drawable.house
            val item = PropertyItem(
                drawable,
                "Fake category: $i with longer text",
                "fake category: $i",
                "fake price: $i"
            )
            list += item
        }

        return list
    }

    private fun setupViewPager(){
        setContentView(R.layout.property_details)
        images = ArrayList()

        images.add(R.drawable.house)
        images.add(R.drawable.bleu)
        images.add(R.drawable.rouge)
        images.add(R.drawable.orange)
        images.add(R.drawable.vert)

        mediaAdapter = MediaAdapter(this,images)
        viewpager.adapter = mediaAdapter

    }

}