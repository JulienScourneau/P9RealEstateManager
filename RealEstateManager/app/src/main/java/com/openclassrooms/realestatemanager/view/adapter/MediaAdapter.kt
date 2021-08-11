package com.openclassrooms.realestatemanager.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
import com.openclassrooms.realestatemanager.data.Photo
import com.openclassrooms.realestatemanager.databinding.MediaItemBinding

class MediaAdapter(
    private val listener: OnItemClickListener,
    private val mediaList: List<Photo>,
    private val hideDeleteButton: Boolean
) : PagerAdapter() {

    override fun getCount(): Int {
        return mediaList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            MediaItemBinding.inflate(LayoutInflater.from(container.context), container, false)
        val media = mediaList[position]

        binding.apply {
            Glide.with(mediaItemImg)
                .load(media.photoReference)
                .apply(RequestOptions.centerInsideTransform())
                .into(mediaItemImg)
            if (hideDeleteButton) {
                mediaItemDeleteBtn.visibility = View.GONE
            } else {
                mediaItemDeleteBtn.setOnClickListener {
                    listener.onItemClick(mediaList[position])
                }
            }
            Log.d("mediaAdapter", "PhotoRef: ${media.photoReference}")

        }
        container.addView(binding.root)
        return binding.root
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }


}