package com.example.gardengatherer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GardenViewAdapter ( val content: Context,
                          val gardens : List<Garden>) : RecyclerView.Adapter<GardenViewAdapter.GardenViewHolder>() {

    // Connect view with view holder

    inner class GardenViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textViewGardenName = itemView.findViewById<TextView>(R.id.textViewGardenName)
        val textViewAuthorName = itemView.findViewById<TextView>(R.id.textViewAuthorName)
        val textViewGardenSize = itemView.findViewById<TextView>(R.id.textViewGardenSize)
        val textViewLighting = itemView.findViewById<TextView>(R.id.textViewLighting)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewAdapter.GardenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_garden_list, parent, false)
        return GardenViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gardens.size
    }

    override fun onBindViewHolder(holder: GardenViewAdapter.GardenViewHolder, position: Int) {
        val garden = gardens[position]
        with (holder){
            textViewGardenName.text = garden.gardenName
            textViewAuthorName.text = garden.userName
            textViewGardenSize.text = garden.gardenSize
            textViewLighting.text = garden.lightConditions
        }
    }
}