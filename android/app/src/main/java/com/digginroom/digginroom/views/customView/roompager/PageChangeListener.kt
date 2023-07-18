package com.digginroom.digginroom.views.customView.roompager

import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class PageChangeListener : ViewPager2.OnPageChangeCallback() {

    override fun onPageScrollStateChanged(state: Int) {
        super.onPageScrollStateChanged(state)
        Log.d("HKHK", "onPageScrollStateChanged $state")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        Log.d("HKHK", "onPageScrolled $position")
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        Log.d("HKHK", "onPageSelected $position")
        // Toast.makeText(context, "$position 번째 화면", Toast.LENGTH_SHORT).show()
        // 뷰 모델의 currentIndex 값 변경
    }
}
