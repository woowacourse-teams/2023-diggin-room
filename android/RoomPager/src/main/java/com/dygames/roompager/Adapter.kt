package com.dygames.roompager

import android.content.Context

interface Adapter {
    interface ViewHolder

    fun createViewHolder(context: Context): ViewHolder
    fun getGridSize(): Int
    fun getItemCount(): Int
    fun onRecycle(currentRoomPosition: Int, recycledViewHolders: List<ViewHolder>)
    fun onLoadNextRoom()
}
