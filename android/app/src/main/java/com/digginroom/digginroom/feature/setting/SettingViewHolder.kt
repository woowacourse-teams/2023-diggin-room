package com.digginroom.digginroom.feature.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemSettingBinding
import com.digginroom.digginroom.model.SettingCategoryModel

class SettingViewHolder(
    private val binding: ItemSettingBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: SettingCategoryModel) {
        with(binding) {
            description = category.description
            settingRvCategoryDetails.adapter = SettingCategoryDetailsAdapter(category.details)
        }
    }

    companion object {

        fun of(parent: ViewGroup): SettingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSettingBinding.inflate(layoutInflater, parent, false)

            return SettingViewHolder(binding)
        }
    }
}
