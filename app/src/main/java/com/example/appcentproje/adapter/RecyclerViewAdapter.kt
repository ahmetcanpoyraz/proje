package com.example.appcentproje.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentproje.R
import com.example.appcentproje.model.GamesModel
import com.example.appcentproje.model.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*
import java.util.logging.Filter

class RecyclerViewAdapter(private val gamesList : ArrayList<Results>,private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>()  {

   interface Listener {
       fun onItemClick(results: Results)
   }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(results: Results,position: Int,listener : Listener){
            itemView.setOnClickListener{
                listener.onItemClick(results)
            }
            itemView.text_nameOfGame.text=results.name
            itemView.text_ratingOfGame.text= results.rating.toString()
            itemView.text_releasedOfGame.text=results.released
            Picasso.get().load(results.background_image).resize(250,250).into(itemView.image_backgrounOfGame)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
       return gamesList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(gamesList[position],position,listener)

    }



}