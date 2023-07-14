package com.digginroom.digginroom

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.model.TempUiModel

class RoomAdapter(private val data: List<TempUiModel>) : RecyclerView.Adapter<RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
