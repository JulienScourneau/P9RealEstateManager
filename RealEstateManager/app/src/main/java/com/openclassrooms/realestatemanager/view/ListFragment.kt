package com.openclassrooms.realestatemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.TestList
import com.openclassrooms.realestatemanager.models.PropertyItem
import com.openclassrooms.realestatemanager.view.adapter.EstateAdapter
import kotlinx.android.synthetic.main.estate_list.*

class ListFragment : Fragment(), EstateAdapter.OnItemClickListener {

    private val testList = generateDummyList(20)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.estate_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerview()

    }

    private fun setUpRecyclerview() {
        recyclerview.adapter = EstateAdapter(TestList.getTestList, this, requireContext())
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.setHasFixedSize(true)

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

    override fun onItemClick(position: Int) {

        Toast.makeText(context, "Click: $position", Toast.LENGTH_SHORT).show()

    }

}