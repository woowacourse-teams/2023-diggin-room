package com.digginroom.digginroom.views.customView.roompager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.views.model.RoomModel

class PagerAdapter(private val data: List<RoomModel>) : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
