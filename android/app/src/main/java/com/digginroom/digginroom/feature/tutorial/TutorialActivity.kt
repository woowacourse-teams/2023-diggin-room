package com.digginroom.digginroom.feature.tutorial

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityTutorialBinding
import com.digginroom.digginroom.feature.room.RoomViewModel
import java.lang.IllegalArgumentException


class TutorialActivity : AppCompatActivity() {
    private val tutorialViewModel: TutorialViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).tutorialViewModelFactory
        )[TutorialViewModel::class.java]
    }
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: ActivityTutorialBinding
    private val fragments: List<Fragment> by lazy {
        listOf(
            TutorialStartFragment(),
            TutorialFragment1(),
            TutorialFragment2(),
            TutorialFragment3(),
            TutorialFragment4(),
            TutorialFragment5()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial)
        viewPager = binding.tutorialVp

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(pageChangeCallback)

        setupOnBoardingIndicators()
        setCurrentOnboardingIndicator(0)
        binding.tvSkip.setOnClickListener {
            tutorialViewModel.completeTutorial()
        }
        tutorialViewModel.tutorialCompleted.observe(this) {
            when (it) {
                is TutorialUiState.Success, is TutorialUiState.Error -> finish()
                is TutorialUiState.Loading -> {}
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setCurrentOnboardingIndicator(position)
        }
    }

    private fun setupOnBoardingIndicators() {
        val indicators =
            arrayOfNulls<ImageView>(fragments.size)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.onboarding_indicator_inactivie
                )
            )

            indicators[i]?.layoutParams = layoutParams

            binding.indicators.addView(indicators[i])
        }
    }

    private fun setCurrentOnboardingIndicator(position: Int) {
        val indicators = binding.indicators.children.toList()
        val activeIndicatorDrawable: Drawable = ContextCompat.getDrawable(
            this,
            R.drawable.onboarding_indicator_active
        ) ?: throw IllegalArgumentException(DRAWABLE_NOT_EXIST_ERROR)
        val inactiveIndicatorDrawable: Drawable = ContextCompat.getDrawable(
            this,
            R.drawable.onboarding_indicator_inactivie
        ) ?: throw IllegalArgumentException(DRAWABLE_NOT_EXIST_ERROR)
        indicators.forEachIndexed { index, view ->
            if (index == position) {
                val imageView = view as ImageView
                imageView.setImageDrawable(activeIndicatorDrawable)
            } else {
                val imageView = view as ImageView
                imageView.setImageDrawable(inactiveIndicatorDrawable)
            }
        }
    }

    companion object {
        const val DRAWABLE_NOT_EXIST_ERROR = "해당 Drawable 파일이 없습니다."
        fun start(context: Context) {
            val intent = Intent(context, TutorialActivity::class.java)
            context.startActivity(intent)
        }
    }
}