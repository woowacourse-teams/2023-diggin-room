package com.digginroom.digginroom.views.customview.roomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.RoomInfoBinding

class RoomInfo(context: Context) : ConstraintLayout(context) {
    private val binding: RoomInfoBinding =
        RoomInfoBinding.inflate(LayoutInflater.from(context), this, true)
//    private val roomInfo: View =
//        LayoutInflater.from(context).inflate(R.layout.room_info, this, true)

    init {
        val typedArray = context.obtainStyledAttributes(R.styleable.RoomInfo)

        val songTitle = typedArray.getString(R.styleable.RoomInfo_songTitle)
        binding.tvTitle.text = songTitle

        val artist = typedArray.getString(R.styleable.RoomInfo_artist)
        binding.tvArtist.text = artist

        val description = typedArray.getString(R.styleable.RoomInfo_description)
        binding.tvDescription.text = description

        typedArray.recycle()
//        setPosition()
    }

//    fun setPosition() {
//        val displayWidth = resources.displayMetrics.widthPixels
//        val displayHeight = resources.displayMetrics.heightPixels
//        roomInfo.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
//        Log.d("HKHK", "높이 ${roomInfo.measuredHeight}")
//        Log.d("HKHK", "넓이 ${roomInfo.measuredWidth}")
//        x =
//            (displayWidth - resources.getDimensionPixelSize(R.dimen.scrap_right_margin) - measuredWidth).toFloat()
//        y =
//            (displayHeight - resources.getDimensionPixelSize(R.dimen.scrap_bottom_margin) - measuredHeight).toFloat()
//    }
}