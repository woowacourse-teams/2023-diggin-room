package com.digginroom.digginroom.views.roompager

import androidx.viewpager2.widget.ViewPager2

class PageChangeListener : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        // Toast.makeText(context, "$position 번째 화면", Toast.LENGTH_SHORT).show()
        // 뷰 모델의 currentIndex 값 변경
    }
}
