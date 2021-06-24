package com.openclassrooms.realestatemanager.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.view.EstateFragment

class EstateAdapter(
    private val estateList: ArrayList<Estate>,
    private val listener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.estate_item,
            parent, false
        )

        return EstateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val currentItem = estateList[position]

        holder.propertyImage.setImageResource(currentItem.photos[0])
        holder.categoryText.text = currentItem.category
        holder.locationText.text = currentItem.address.street
        holder.priceText.text = currentItem.price

        holder.itemView.setOnClickListener {
            val activity = context as AppCompatActivity
            val fragment = EstateFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit()
        }

    }

    override fun getItemCount() = estateList.size


    inner class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
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