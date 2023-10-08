package com.digginroom.digginroom.feature.setting

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.model.SettingCategoryModel

class SettingCategoryAdapter(
    private val categories: List<SettingCategoryModel>
) : RecyclerView.Adapter<SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        return SettingViewHolder.of(parent)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
