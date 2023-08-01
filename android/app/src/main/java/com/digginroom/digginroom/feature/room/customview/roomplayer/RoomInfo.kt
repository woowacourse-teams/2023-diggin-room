package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.databinding.RoomInfoBinding
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.SongModel

class RoomInfo(context: Context) : ConstraintLayout(context) {
    private val binding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)
    var myHeight = 0
        private set

    init {
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        myHeight = measuredHeight
    }

    fun setRoomInfo(room: RoomModel) {
        setSongInfo(room.song)
        setScrap(room.isScrapped)
    }

    private fun setSongInfo(song: SongModel) {
        binding.tvTitle.text = song.title
        binding.tvArtist.text = song.artist
        binding.tvDescription.text = song.albumTitle + song.genres + song.tags
    }

    private fun setScrap(isScrapped: Boolean) {
        binding.scrapToggle.isScrapped = isScrapped
    }
}
