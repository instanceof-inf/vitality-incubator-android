package com.johan.blignaut.discovery.vitalityincubator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johan.blignaut.discovery.vitalityincubator.R
import com.johan.blignaut.discovery.vitalityincubator.models.City
import com.squareup.picasso.Picasso

class PlacesAdapter(
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    val items = mutableListOf<City>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: LinearLayout = view.findViewById(R.id.lnrPlaceItem)
        val name: TextView = view.findViewById(R.id.lblCityName)
        val img: ImageView = view.findViewById(R.id.imgCity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            item.setOnClickListener {
                onClickListener.invoke(items[position].country)
            }
            name.text = items[position].name
            Picasso.get()
                .load(items[position].image)
                .placeholder(R.drawable.ic_launcher_foreground)//it will show placeholder image when url is not valid.
//                .networkPolicy(NetworkPolicy.OFFLINE) //for caching the image url in case phone is offline
                .into(viewHolder.img)
        }
    }

    override fun getItemCount() = items.size
}