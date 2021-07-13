package com.openclassrooms.realestatemanager.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.EstateItemBinding
import com.openclassrooms.realestatemanager.data.EstateWithPhoto


class EstateAdapter(private val listener: OnItemClickListener) :
    ListAdapter<EstateWithPhoto, EstateAdapter.EstateViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val binding = EstateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstateViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val currentEstate = getItem(position)
        holder.bind(currentEstate)
    }

    inner class EstateViewHolder(private val binding: EstateItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.propertyItem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            listener.onItemClick(position)
        }

        fun bind(estate: EstateWithPhoto) {
            Glide.with(itemView)
                .load(estate.photos.first())
                .into(binding.propertyItemImage)
            binding.apply {
                propertyItemCategory.text = estate.estate.category
                propertyItemLocation.text = estate.estate.address.street
                propertyItemPrice.text = estate.estate.price
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class DiffCallback : DiffUtil.ItemCallback<EstateWithPhoto>() {
        override fun areItemsTheSame(oldItem: EstateWithPhoto, newItem: EstateWithPhoto) =
            oldItem.estate.id == newItem.estate.id

        override fun areContentsTheSame(oldItem: EstateWithPhoto, newItem: EstateWithPhoto) =
            oldItem == newItem
    }

}

