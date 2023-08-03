package com.digginroom.digginroom.feature.scrap.navigator

import com.digginroom.digginroom.model.RoomModel

interface ScrapNavigator {

    fun navigate(
        rooms: List<RoomModel>,
        position: Int
    )
}
