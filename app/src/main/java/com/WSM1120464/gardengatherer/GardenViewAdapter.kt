package com.WSM1120464.gardengatherer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Updates main activity with card for each garden
 */
class GardenViewAdapter ( val context: Context,
                          val gardens : List<Garden>,
                          val itemListener : GardenItemListener) : RecyclerView.Adapter<GardenViewAdapter.GardenViewHolder>() {

    // Connect view with view holder
    inner class GardenViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textViewGardenName = itemView.findViewById<TextView>(R.id.textViewGardenName)
        val textViewGardenSize = itemView.findViewById<TextView>(R.id.textViewGardenSize)
        val textViewLighting = itemView.findViewById<TextView>(R.id.textViewLighting)
        val textViewGardenNotes = itemView.findViewById<TextView>(R.id.textViewGardenNotes)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_garden_list, parent, false)
        return GardenViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gardens.size
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        val garden = gardens[position]
        with (holder){
            textViewGardenName.text = garden.gardenName
            textViewGardenSize.text = garden.gardenSize
            textViewLighting.text = garden.lightConditions
        }

        holder.itemView.setOnClickListener {
            itemListener.gardenSelected(garden)
        }
    }

    interface GardenItemListener {
        fun gardenSelected(garden : Garden)
    }
}