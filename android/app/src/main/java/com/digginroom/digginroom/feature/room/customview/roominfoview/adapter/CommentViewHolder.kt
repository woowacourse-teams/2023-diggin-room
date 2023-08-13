package com.digginroom.digginroom.feature.room.customview.roominfoview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.databinding.ItemCommentBinding
import com.digginroom.digginroom.model.CommentModel

class CommentViewHolder(private val binding: ItemCommentBinding) : ViewHolder(binding.root) {
    fun bind(item: CommentModel) {
        binding.comment = item
    }

    companion object {
        fun of(parent: ViewGroup): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
            return CommentViewHolder(binding)
        }
    }
}
