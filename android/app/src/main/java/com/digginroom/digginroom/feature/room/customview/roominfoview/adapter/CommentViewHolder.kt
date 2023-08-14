package com.digginroom.digginroom.feature.room.customview.roominfoview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.databinding.ItemCommentBinding
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowCommentMenuListener
import com.digginroom.digginroom.model.CommentModel

class CommentViewHolder(private val binding: ItemCommentBinding) : ViewHolder(binding.root) {
    fun bind(item: CommentModel, position: Int) {
        binding.comment = item
        binding.selectedPosition = position
    }

    companion object {
        fun of(parent: ViewGroup, menuClickListener: ShowCommentMenuListener): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
            binding.showMenuListener = menuClickListener
            return CommentViewHolder(binding)
        }
    }
}
