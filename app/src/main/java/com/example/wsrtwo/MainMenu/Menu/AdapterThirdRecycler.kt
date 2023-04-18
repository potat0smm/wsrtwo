package com.example.wsrtwo.MainMenu.Menu

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wsrtwo.Data.CatalogItem
import com.example.wsrtwo.R
import com.google.android.material.button.MaterialButton

class AdapterThirdRecycler(var analysisList: List<CatalogItem>,
                            val addBtn:MaterialButton,
                            val FL: FrameLayout
                            ):RecyclerView.Adapter<AdapterThirdRecycler.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.third_name)
        val time: TextView = itemView.findViewById(R.id.third_time)
        val price: TextView = itemView.findViewById(R.id.third_price)
        val add: MaterialButton = itemView.findViewById(R.id.button_recycler)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.third_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return analysisList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = analysisList[position]
        holder.name.text = item.name
        holder.price.text = item.price
        holder.time.text = item.time_result


        if (item.Button) {
            holder.add.text = "Убрать"
            holder.add.setBackgroundResource(R.drawable.background_button)
            holder.add.setTextColor(Color.parseColor("#007AFF"))
        } else {
            holder.add.text = "Добавить"
            holder.add.setBackgroundResource(R.drawable.background_button)
            holder.add.setTextColor(Color.parseColor("#FFFFFFFF"))
        }

        holder.add.setOnClickListener {

            item.Button = !item.Button // изменение состояния элемента списка

            if (item.Button) {
                holder.add.text = "Убрать"
                addBtn.visibility = View.VISIBLE
                FL.visibility = View.VISIBLE
                holder.add.setBackgroundResource(R.drawable.background_button)
                holder.add.setTextColor(Color.parseColor("#007AFF"))
            } else {
                holder.add.text = "Добавить"
                addBtn.visibility = View.INVISIBLE
                FL.visibility = View.INVISIBLE
                holder.add.setBackgroundResource(R.drawable.background_button)
                holder.add.setTextColor(Color.parseColor("#FFFFFFFF"))
            }
        }
    }
}