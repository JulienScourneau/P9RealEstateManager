package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), PropertyAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private val testList = generateDummyList(20)
    private val adapter = PropertyAdapter(testList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Click: $position", Toast.LENGTH_SHORT).show()
    }

    private fun generateDummyList(size: Int): ArrayList<PropertyRecyclerview> {
        val list = ArrayList<PropertyRecyclerview>()

        for (i in 0 until size) {
            val drawable = R.mipmap.ic_launcher
            val item = PropertyRecyclerview(drawable, "Fake category: $i", "fake category: $i", "fake price: $i")
            list += item
        }

        return list
    }
}