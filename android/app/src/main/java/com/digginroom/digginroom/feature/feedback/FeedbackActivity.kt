package com.digginroom.digginroom.feature.feedback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityFeedbackBinding
import com.digginroom.digginroom.model.FeedbackModel
import com.dygames.androiddi.ViewModelDependencyInjector

class FeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private val feedbackViewModel: FeedbackViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelDependencyInjector.injectViewModel<FeedbackViewModel>()
        )[FeedbackViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initFeedbackBinding()
    }

    private fun initLayout() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun initFeedbackBinding() {
        binding = DataBindingUtil.setContentView<ActivityFeedbackBinding>(
            this,
            R.layout.activity_feedback
        ).also {
            it.lifecycleOwner = this
            it.feedbackViewModel = feedbackViewModel
            it.feedback = FeedbackModel()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }
}
