package com.johan.blignaut.discovery.vitalityincubator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johan.blignaut.discovery.vitalityincubator.R
import com.johan.blignaut.discovery.vitalityincubator.models.CardBack
import com.squareup.picasso.Picasso

class CardBacksAdapter(
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<CardBacksAdapter.ViewHolder>() {

    val items = mutableListOf<CardBack>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: LinearLayout = view.findViewById(R.id.lnrCardBackItem)
        val name: TextView = view.findViewById(R.id.lblCardBackName)
        val img: ImageView = view.findViewById(R.id.imgCardBack)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cardback_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            item.setOnClickListener {
                onClickListener.invoke(items[position].description)
            }
            name.text = items[position].name
            Picasso.get()
                .load(items[position].img)
                .placeholder(R.drawable.ic_launcher_foreground)//it will show placeholder image when url is not valid.
//                .networkPolicy(NetworkPolicy.OFFLINE) //for caching the image url in case phone is offline
                .into(viewHolder.img)
//            Picasso
//                .get()
//                .load("https://d15f34w2p8l1cc.cloudfront.net/hearthstone/556d677acbb31fececd42912cf003aabeb3bb6efb571bb43de83e6b763763f2c.png")
//                .into(viewHolder.img)
        }
    }

    override fun getItemCount() = items.size
}