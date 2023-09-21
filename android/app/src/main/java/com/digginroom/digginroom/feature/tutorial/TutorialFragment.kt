package com.digginroom.digginroom.feature.tutorial

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.FragmentTutorialBinding

class TutorialFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentTutorialBinding
    private val fragments: List<Fragment> by lazy {
        listOf(
            TutorialFragment1(),
            TutorialFragment2(),
            TutorialFragment3()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTutorialBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewPager()
        binding.tutorialTvSkip.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this).commit()
            fragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun initViewPager() {
        viewPager = binding.tutorialViewpagerTutorial

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity(), fragments)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (position == fragments.size - 1) {
                    binding.tutorialTvSkip.text = getString(R.string.tutorial_start)
                } else {
                    binding.tutorialTvSkip.text = getString(R.string.tutorial_skip)
                }
            }
        })

        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(fragments.size)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            val imageView = ImageView(requireContext())
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_indicator_inactivie
                )
            )

            imageView.layoutParams = layoutParams

            binding.tutorialLayoutIndicators.addView(imageView)
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val indicators = binding.tutorialLayoutIndicators.children.toList()
        val activeIndicatorDrawable: Drawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_indicator_active
        ) ?: throw IllegalArgumentException(DRAWABLE_NOT_EXIST_ERROR)
        val inactiveIndicatorDrawable: Drawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_indicator_inactivie
        ) ?: throw IllegalArgumentException(DRAWABLE_NOT_EXIST_ERROR)
        indicators.forEachIndexed { index, view ->
            val imageView = view as ImageView
            if (index == position) {
                imageView.setImageDrawable(activeIndicatorDrawable)
            } else {
                imageView.setImageDrawable(inactiveIndicatorDrawable)
            }
        }
    }

    companion object {
        const val DRAWABLE_NOT_EXIST_ERROR = "해당 Drawable 파일이 없습니다."
    }
}
