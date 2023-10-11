package com.digginroom.digginroom.feature.setting

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.model.SettingCategoryDetailModel

class SettingCategoryDetailsAdapter(private val details: List<SettingCategoryDetailModel>) :
    RecyclerView.Adapter<SettingCategoryDetailViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingCategoryDetailViewHolder {
        return SettingCategoryDetailViewHolder.of(parent)
    }

    override fun onBindViewHolder(holder: SettingCategoryDetailViewHolder, position: Int) {
        holder.bind(details[position])
    }

    override fun getItemCount(): Int = details.size
}
