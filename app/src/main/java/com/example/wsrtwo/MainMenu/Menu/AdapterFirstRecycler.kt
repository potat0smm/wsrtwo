package com.example.wsrtwo.MainMenu.Menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsrtwo.Data.NewsItem
import com.example.wsrtwo.R
import com.squareup.picasso.Picasso

class AdapterFirstRecycler(var newsList: List<NewsItem>):RecyclerView.Adapter<AdapterFirstRecycler.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.first_name)
        val title:TextView = itemView.findViewById(R.id.first_title)
        val price:TextView = itemView.findViewById(R.id.first_price)
        val image:ImageView = itemView.findViewById(R.id.first_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_first_recycler,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = newsList[position]
        holder.name.text = item.name
        holder.title.text = item.description
        holder.price.text = item.price
        Picasso.get().load(item.image).into(holder.image)
    }
}