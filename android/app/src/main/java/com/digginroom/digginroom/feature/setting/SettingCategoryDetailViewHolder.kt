package com.digginroom.digginroom.feature.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemSettingDetailBinding
import com.digginroom.digginroom.model.SettingCategoryDetailModel

class SettingCategoryDetailViewHolder(
    private val binding: ItemSettingDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(detail: SettingCategoryDetailModel) {
        binding.detail = detail
    }

    companion object {

        fun of(parent: ViewGroup): SettingCategoryDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSettingDetailBinding.inflate(layoutInflater, parent, false)

            return SettingCategoryDetailViewHolder(binding)
        }
    }
}
