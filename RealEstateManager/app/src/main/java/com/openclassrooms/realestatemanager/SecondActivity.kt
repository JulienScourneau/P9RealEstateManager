package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.property_details)

        prepopulateScrollView()

    }

    private fun prepopulateScrollView() {
        val gallery: LinearLayout = findViewById(R.id.gallery)

        val inflater = LayoutInflater.from(this)

        for (i in 0 until 6) {
            var view = inflater.inflate(R.layout.media_item, gallery, false)

            val drawable = when (i % 3) {
                0 -> R.drawable.image_unavailable
                1 -> R.drawable.baseline_home_black_48
                else -> R.drawable.baseline_hotel_black_48
            }

            val imageView: ImageView = view.findViewById(R.id.media_item_image)
            imageView.setImageResource(drawable)

            gallery.addView(view)
        }
    }
}