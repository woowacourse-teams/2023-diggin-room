package com.dygames.roompager

import android.content.Context

interface Adapter<out T : Adapter.ViewHolder> {
    interface ViewHolder

    fun createViewHolder(context: Context): T
    fun getItemCount(): Int
    fun onRecycle(pagingOrientation: PagingOrientation, currentRoomPosition: Int, recycledViewHolders: List<ViewHolder>)
    fun onLoadNextRoom()
}
