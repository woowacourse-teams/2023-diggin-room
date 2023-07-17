package com.digginroom.digginroom.viewmodels

class YoutubeRoomCacheStrategy(
    override val previousRoomCount: Int = 2,
    override val nextRoomCount: Int = 2
) : RoomCacheStrategy
