package com.openclassrooms.realestatemanager.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.openclassrooms.realestatemanager.databinding.MediaItemBinding

class MediaAdapter(private val context: Context, private val mediaList: ArrayList<Int>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return mediaList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val binding = MediaItemBinding.inflate(LayoutInflater.from(context), container, false)
        val media = mediaList[position]

        binding.mediaItemImg.setImageResource(media)
        container.addView(binding.root)

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}