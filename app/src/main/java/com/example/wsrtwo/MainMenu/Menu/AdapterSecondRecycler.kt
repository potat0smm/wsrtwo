package com.example.wsrtwo.MainMenu.Menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wsrtwo.databinding.SecondRecyclerviewBinding

class AdapterSecondRecycler(private val onItemClick:(String) -> Unit):RecyclerView.Adapter<AdapterSecondRecycler.ViewHolder>(){

    private val filter = mutableListOf("Популярные", "COVID","ЗОЖ","Онкогенетические")
    private var selectedFilter: String? = null
    inner class ViewHolder(private val binding:SecondRecyclerviewBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: String){
            binding.category.text = category
            if(category == selectedFilter){
                binding.root.setCardBackgroundColor(Color.parseColor("#F5F5F9"))
                binding.category.setTextColor(Color.parseColor("#7E7E9A"))
            }else{
                binding.root.setCardBackgroundColor(Color.parseColor("#1A6FEE"))
                binding.category.setTextColor(Color.parseColor("#FFFFFF"))
            }
            binding.root.setOnClickListener{
                val fil = selectedFilter
                selectedFilter = category
                onItemClick(category)
                if(fil!=null){
                    notifyItemChanged(filter.indexOf(fil))
                }
                notifyItemChanged(filter.indexOf(category))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = SecondRecyclerviewBinding.inflate(inflate,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filter[position])
    }
}