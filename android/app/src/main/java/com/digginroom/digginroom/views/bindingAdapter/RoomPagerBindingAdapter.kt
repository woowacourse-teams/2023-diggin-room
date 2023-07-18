package com.digginroom.digginroom.views.bindingAdapter

import androidx.databinding.BindingAdapter
import com.digginroom.digginroom.views.customView.roompager.RoomPager
import com.digginroom.digginroom.views.model.RoomModel

object RoomPagerBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:data")
    fun data(roomPager: RoomPager, data: List<RoomModel>) {
        roomPager.data = data
    }
}
