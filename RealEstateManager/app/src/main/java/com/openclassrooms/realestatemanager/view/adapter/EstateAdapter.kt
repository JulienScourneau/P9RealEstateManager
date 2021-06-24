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
import com.openclassrooms.realestatemanager.databinding.EstateItemBinding
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.view.EstateFragment

class EstateAdapter(
    private val estateList: ArrayList<Estate>,
    private val listener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<EstateAdapter.EstateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        val binding = EstateItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return EstateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        with(holder) {
            with(estateList[position]) {
                binding.propertyItemImage.setImageResource(this.photos[0])
                binding.propertyItemCategory.text = this.category
                binding.propertyItemLocation.text = this.address.street
                binding.propertyItemPrice.text = this.price

                holder.itemView.setOnClickListener {
                    val activity = context as AppCompatActivity
                    val fragment = EstateFragment()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            }
        }


    }

    override fun getItemCount() = estateList.size


    inner class EstateViewHolder(val binding: EstateItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {


        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}