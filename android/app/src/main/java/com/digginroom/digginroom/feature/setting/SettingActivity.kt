package com.digginroom.digginroom.feature.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivitySettingBinding
import com.digginroom.digginroom.feature.feedback.FeedbackActivity
import com.digginroom.digginroom.feature.login.LoginActivity
import com.digginroom.digginroom.model.SettingCategoryDetailModel
import com.digginroom.digginroom.model.SettingCategoryModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var logoutConfirmDialog: AlertDialog
    private val settingViewModel: SettingViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<SettingViewModel>()
        )[SettingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSettingBinding()
        initSettingView()
        initLogoutDialog()
        initSettingObserver()
    }

    private fun initSettingBinding() {
        binding =
            DataBindingUtil.setContentView<ActivitySettingBinding>(this, R.layout.activity_setting)
                .also {
                    it.lifecycleOwner = this
                    it.settingViewModel = settingViewModel
                }
    }

    private fun initSettingView() {
        binding.settingRvSetttingCategory.adapter =
            SettingCategoryAdapter(
                categories = listOf(
                    SettingCategoryModel.Account(
                        listOf(
                            SettingCategoryDetailModel(
                                description = R.string.common_logout,
                                descriptionImg = R.drawable.ic_logout,
                                onClick = settingViewModel::startLogout
                            )
                        )
                    ),
                    SettingCategoryModel.Etc(
                        listOf(
                            SettingCategoryDetailModel(
                                description = R.string.common_feedback,
                                descriptionImg = R.drawable.ic_feedback,
                                onClick = { FeedbackActivity.start(this) }
                            )
                        )
                    )
                )
            )
    }

    private fun initLogoutDialog() {
        logoutConfirmDialog = AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.logout_confirm)).setPositiveButton(getString(R.string.common_yes)) { _, _ ->
                settingViewModel.logout()
            }.setNegativeButton(getString(R.string.common_no)) { _, _ ->
            }
        }.create()
    }

    private fun initSettingObserver() {
        settingViewModel.uiState.observe(this) {
            when (settingViewModel.uiState.value as SettingUiState) {
                SettingUiState.Logout.InProgress -> logoutConfirmDialog.show()
                SettingUiState.Logout.Done -> LoginActivity.start(this)
                SettingUiState.Cancel -> finish()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
