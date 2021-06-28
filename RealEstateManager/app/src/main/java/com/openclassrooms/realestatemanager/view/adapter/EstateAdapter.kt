package com.openclassrooms.realestatemanager.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.EstateItemBinding
import com.openclassrooms.realestatemanager.models.Estate


class EstateAdapter(
    private val estateList: ArrayList<Estate>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder(
            EstateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        with(holder) {
            with(estateList[position]) {
                binding.propertyItemImage.setImageResource(this.photos[0])
                binding.propertyItemCategory.text = this.category
                binding.propertyItemLocation.text = this.address.street
                binding.propertyItemPrice.text = this.price

            }
        }


    }

    override fun getItemCount(): Int {
        return estateList.size
    }


    inner class EstateViewHolder(val binding: EstateItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.propertyItem.setOnClickListener(this)
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

