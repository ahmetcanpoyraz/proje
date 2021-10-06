package com.example.appcentproje.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentproje.R
import com.example.appcentproje.view.DetailActivity
import com.squareup.picasso.Picasso

class ViewPagerAdapter ( private var images: List<String>,private var id: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemImage: ImageView = itemView.findViewById(R.id.image_itempage)
        
        init {
            itemImage.setOnClickListener { v: View ->
                val position = adapterPosition
                val intent =Intent(itemView.context,DetailActivity::class.java)
                intent.putExtra("idOfGame",id.get(position))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page_viewpager,parent,false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        Picasso.get().load(images[position]).into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}