package com.openclassrooms.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Click: $position", Toast.LENGTH_SHORT).show()
    }

    private fun generateDummyList(size: Int): ArrayList<PropertyItem> {
        val list = ArrayList<PropertyItem>()

        for (i in 0 until size) {
            val drawable = R.mipmap.ic_launcher
            val item = PropertyItem(drawable, "Fake category: $i", "fake category: $i", "fake price: $i")
            list += item
        }

        return list
    }

}