package com.openclassrooms.realestatemanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.EstateItemBinding
import com.openclassrooms.realestatemanager.data.EstateWithPhoto
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

        fun bind(estateWithPhoto: EstateWithPhoto) {
            binding.apply {
                estateWithPhoto.apply {
                    if (estateWithPhoto.photosList.isEmpty()) {
                        Glide.with(itemView)
                            .load(R.drawable.no_image_available)
                            .apply(RequestOptions.centerCropTransform())
                            .into(propertyItemImage)
                    } else {
                        Glide.with(itemView)
                            .load(estateWithPhoto.photosList[0].photoReference)
                            .apply(RequestOptions.centerCropTransform())
                            .into(propertyItemImage)
                    }
                    estate.apply {
                        propertyItemCategory.text = if (category.isNotBlank()) category else "N/A"
                        propertyItemLocation.text =
                            if (address.street.isNotBlank()) address.street else "N/A"
                        propertyItemPrice.text =
                            if (price.isNotBlank()) Utils.formatPrice(price) else "N/A"
                    }
                }
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