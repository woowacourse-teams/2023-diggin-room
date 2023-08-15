package com.digginroom.digginroom.feature.room.customview.roominfoview.comment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.digginroom.digginroom.databinding.ItemCommentBinding
import com.digginroom.digginroom.model.CommentModel

class CommentViewHolder(
    private val binding: ItemCommentBinding,
    private val onClick: (Int) -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.showMenuListener = {
            onClick(adapterPosition)
        }
    }

    fun bind(item: CommentModel) {
        binding.comment = item
    }

    companion object {
        fun of(parent: ViewGroup, onClick: (Int) -> Unit): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCommentBinding.inflate(layoutInflater, parent, false)
            return CommentViewHolder(binding, onClick)
        }
    }
}
