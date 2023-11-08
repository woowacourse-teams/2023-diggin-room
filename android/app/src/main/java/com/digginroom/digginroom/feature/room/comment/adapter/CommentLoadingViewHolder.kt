package com.digginroom.digginroom.feature.room.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.databinding.ItemCommentLoadingBinding

class CommentLoadingViewHolder(
    binding: ItemCommentLoadingBinding
) : ViewHolder(binding.root) {

    companion object {
        fun of(parent: ViewGroup): CommentLoadingViewHolder {
            val binding = ItemCommentLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CommentLoadingViewHolder(binding)
        }
    }
}
