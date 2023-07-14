package com.digginroom.digginroom

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.model.TempUiModel

class PagerAdapter(private val data: List<TempUiModel>) : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
