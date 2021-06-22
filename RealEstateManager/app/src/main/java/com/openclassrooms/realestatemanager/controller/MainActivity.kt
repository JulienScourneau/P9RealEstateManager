package com.openclassrooms.realestatemanager.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.view.ListFragment
import kotlinx.android.synthetic.main.activity_main.*


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