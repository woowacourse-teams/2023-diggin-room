package com.digginroom.digginroom

import android.os.Bundle
import android.view.GestureDetector
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.model.TempUiModel

class RoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)

        val data = listOf(
            TempUiModel(R.drawable.kitty),
            TempUiModel(R.drawable.poppy),
            TempUiModel(R.drawable.kitty),
            TempUiModel(R.drawable.poppy),
        )

        val gestureDetector = GestureDetector(this, ScrollGestureListener(binding.viewPager))

        initViewPagerView(data, gestureDetector)
    }

    private fun initViewPagerView(data: List<TempUiModel>, gestureDetector: GestureDetector) {
        binding.viewPager.apply {
            adapter = PagerAdapter(data)
            // 사용자 제스쳐에 따라 가로, 세로 스크롤 변경
            getChildAt(0).setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                performClick()
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Toast.makeText(context, "$position 번째 화면", Toast.LENGTH_SHORT).show()
                    // 뷰 모델의 currentIndex 값 변경
                }
            })
        }
    }
}
