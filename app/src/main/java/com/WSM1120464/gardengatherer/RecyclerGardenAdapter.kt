package com.WSM1120464.gardengatherer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerGardenAdapter (val context: Context,
                                val gardens : List<Garden>,
                                val itemListener : GardenItemListener) : RecyclerView.Adapter<RecyclerGardenAdapter.GardenViewHolder>()
{
    inner class GardenViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val gardenNameTextView = itemView.findViewById<TextView>(R.id.textViewGardenName)
        val authorNameTextView = itemView.findViewById<TextView>(R.id.textViewAuthorName)
        val gardenSizeTextView = itemView.findViewById<TextView>(R.id.textViewGardenSize)
        val lightingTextView = itemView.findViewById<TextView>(R.id.textViewLighting)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_garden_list, parent, false)
        return GardenViewHolder(view)
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        val garden = gardens[position]
        with (holder){
            gardenNameTextView.text = garden.gardenName
            authorNameTextView.text = garden.userName
            gardenSizeTextView.text = garden.gardenSize
            lightingTextView.text = garden.lightConditions
        }
    }

    override fun getItemCount(): Int {
        return gardens.size
    }

    interface GardenItemListener{
        fun gardenSelected(garden : Garden)
    }
}