package com.openclassrooms.realestatemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PropertyAdapter(
        private val propertyList: List<PropertyItem>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.property_item,
                parent, false)

        return PropertyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val currentItem = propertyList[position]

        holder.propertyImage.setImageResource(currentItem.imageResource)
        holder.categoryText.text = currentItem.category
        holder.locationText.text = currentItem.location
        holder.priceText.text = currentItem.price

    }

    override fun getItemCount() = propertyList.size


    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        val propertyImage: ImageView = itemView.findViewById(R.id.property_item_image)
        val categoryText: TextView = itemView.findViewById(R.id.property_item_category)
        val locationText: TextView = itemView.findViewById(R.id.property_item_location)
        val priceText: TextView = itemView.findViewById(R.id.property_item_price)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}