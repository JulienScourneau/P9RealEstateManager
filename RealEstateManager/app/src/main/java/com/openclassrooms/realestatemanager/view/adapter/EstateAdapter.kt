package com.openclassrooms.realestatemanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.models.EstateWithPhoto
import com.openclassrooms.realestatemanager.databinding.EstateItemBinding
import com.openclassrooms.realestatemanager.utils.Utils

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
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val estate = getItem(position)
                        listener.onItemClick(estate)
                    }
                }
            }
        }

        fun bind(estate: EstateWithPhoto) {
            binding.apply {
                if (estate.photosList.isEmpty()) {
                    Glide.with(itemView)
                        .load(R.drawable.image_unavailable)
                        .apply(RequestOptions.centerCropTransform())
                        .into(propertyItemImage)
                } else {
                    Glide.with(itemView)
                        .load(estate.photosList[0].photoReference)
                        .apply(RequestOptions.centerCropTransform())
                        .into(propertyItemImage)
                }

                propertyItemCategory.text = estate.estate.category
                propertyItemLocation.text = estate.estate.address.street
                propertyItemPrice.text = Utils.formatPrice(estate.estate.price)

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(estate: EstateWithPhoto)
    }

    class DiffCallback : DiffUtil.ItemCallback<EstateWithPhoto>() {
        override fun areItemsTheSame(oldItem: EstateWithPhoto, newItem: EstateWithPhoto) =
            oldItem.estate.id == newItem.estate.id

        override fun areContentsTheSame(oldItem: EstateWithPhoto, newItem: EstateWithPhoto) =
            oldItem == newItem
    }
}