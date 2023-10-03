package com.digginroom.digginroom.feature.room.comment.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.model.CommentModel

class CommentAdapter(private val menuClickListener: (CommentModel) -> Unit) :
    ListAdapter<CommentModel, CommentViewHolder>(CommentDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        Log.d("HKHK", "onCreateViewHolder: ")
        return CommentViewHolder.of(parent) {
            menuClickListener(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
