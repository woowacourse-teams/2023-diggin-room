package com.digginroom.digginroom.feature.room

interface RoomInfoListener {
    fun scrap(roomId: Long)
    fun cancelScrap(roomId: Long)
}