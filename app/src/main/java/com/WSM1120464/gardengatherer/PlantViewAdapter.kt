package com.WSM1120464.gardengatherer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Populate card with plant information
 */
class PlantViewAdapter (val context: Context,
                        val plants : List<Plant>,
                        val itemListener : PlantItemListener) : RecyclerView.Adapter<PlantViewAdapter.PlantViewHolder>() {

    inner class PlantViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val plantNameTextView = itemView.findViewById<TextView>(R.id.textViewPlantName)
        val plantTypeTextView = itemView.findViewById<TextView>(R.id.textViewPlantType)
        val plantHeightTextView = itemView.findViewById<TextView>(R.id.textViewPlantHeight)
        val plantLightTextView = itemView.findViewById<TextView>(R.id.textViewPlantLight)
        val imageBloomJan = itemView.findViewById<ImageView>(R.id.imageViewJan)
        val imageBloomFeb = itemView.findViewById<ImageView>(R.id.imageViewFeb)
        val imageBloomMar = itemView.findViewById<ImageView>(R.id.imageViewMar)
        val imageBloomApr = itemView.findViewById<ImageView>(R.id.imageViewApr)
        val imageBloomMay = itemView.findViewById<ImageView>(R.id.imageViewMay)
        val imageBloomJun = itemView.findViewById<ImageView>(R.id.imageViewJun)
        val imageBloomJul = itemView.findViewById<ImageView>(R.id.imageViewJul)
        val imageBloomAug = itemView.findViewById<ImageView>(R.id.imageViewAug)
        val imageBloomSep = itemView.findViewById<ImageView>(R.id.imageViewSep)
        val imageBloomOct = itemView.findViewById<ImageView>(R.id.imageViewOct)
        val imageBloomNov = itemView.findViewById<ImageView>(R.id.imageViewNov)
        val imageBloomDec = itemView.findViewById<ImageView>(R.id.imageViewDec)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_plant_list, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        with (holder){
            plantNameTextView.text = plant.plantName
            plantTypeTextView.text = plant.plantType
            plantHeightTextView.text = plant.plantHeight
            plantLightTextView.text = plant.plantLight

            // establish all months as out of bloom
            imageBloomJan.setImageResource(R.drawable.out_of_bloom)
            imageBloomFeb.setImageResource(R.drawable.out_of_bloom)
            imageBloomMar.setImageResource(R.drawable.out_of_bloom)
            imageBloomApr.setImageResource(R.drawable.out_of_bloom)
            imageBloomMay.setImageResource(R.drawable.out_of_bloom)
            imageBloomJun.setImageResource(R.drawable.out_of_bloom)
            imageBloomJul.setImageResource(R.drawable.out_of_bloom)
            imageBloomAug.setImageResource(R.drawable.out_of_bloom)
            imageBloomSep.setImageResource(R.drawable.out_of_bloom)
            imageBloomOct.setImageResource(R.drawable.out_of_bloom)
            imageBloomNov.setImageResource(R.drawable.out_of_bloom)
            imageBloomDec.setImageResource(R.drawable.out_of_bloom)

            // set months within start - finish as in bloom
            for (i in plant.plantBloomStart!!..plant.plantBloomEnd!!)
            {
                when (i)
                {
                    1 -> imageBloomJan.setImageResource(R.drawable.in_bloom)
                    2-> imageBloomFeb.setImageResource(R.drawable.in_bloom)
                    3-> imageBloomMar.setImageResource(R.drawable.in_bloom)
                    4-> imageBloomApr.setImageResource(R.drawable.in_bloom)
                    5-> imageBloomMay.setImageResource(R.drawable.in_bloom)
                    6-> imageBloomJun.setImageResource(R.drawable.in_bloom)
                    7-> imageBloomJul.setImageResource(R.drawable.in_bloom)
                    8-> imageBloomAug.setImageResource(R.drawable.in_bloom)
                    9-> imageBloomSep.setImageResource(R.drawable.in_bloom)
                    10-> imageBloomOct.setImageResource(R.drawable.in_bloom)
                    11-> imageBloomNov.setImageResource(R.drawable.in_bloom)
                    12-> imageBloomDec.setImageResource(R.drawable.in_bloom)
                }
            }

            holder.itemView.setOnClickListener {
                itemListener.plantSelected(plant)
            }
        }
    }

    interface PlantItemListener {
        fun plantSelected(plant : Plant)
    }
}