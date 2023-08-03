package com.digginroom.digginroom.feature.room

interface ScrapListener {
    fun scrap(roomId: Long)
    fun cancelScrap(roomId: Long)
}
