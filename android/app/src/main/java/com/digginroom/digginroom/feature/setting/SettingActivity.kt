package com.digginroom.digginroom.feature.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivitySettingBinding
import com.digginroom.digginroom.model.SettingCategoryModel

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSettingBinding()
        initSettingView()
    }

    private fun initSettingBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)
    }

    private fun initSettingView() {
        binding.settingRvSetttingCategory.adapter = SettingCategoryAdapter(
            categories = listOf(
                SettingCategoryModel.Account,
                SettingCategoryModel.Etc
            )
        )
    }
}
