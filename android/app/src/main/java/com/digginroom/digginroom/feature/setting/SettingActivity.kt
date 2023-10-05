package com.digginroom.digginroom.feature.setting

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivitySettingBinding
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
                    it.viewModel = settingViewModel
                }
    }

    private fun initSettingView() {
        binding.settingRvSetttingCategory.adapter = SettingCategoryAdapter(
            categories = listOf(
                SettingCategoryModel.Account(
                    listOf(
                        SettingCategoryDetailModel(
                            description = R.string.common_logout,
                            descriptionImg = R.drawable.ic_logout,
                            invoke = settingViewModel::startLogout
                        )
                    )
                ),
                SettingCategoryModel.Etc(
                    listOf(
                        SettingCategoryDetailModel(
                            description = R.string.common_feedback,
                            descriptionImg = R.drawable.ic_logout
                        )
                    )
                )
            )
        )
    }

    private fun initLogoutDialog() {
        logoutConfirmDialog = AlertDialog.Builder(this)
            .apply {
                setMessage(LOGOUT_CONFIRM_MESSAGE)
                    .setPositiveButton(getString(R.string.common_yes)) { _, _ ->
                        settingViewModel.logout()
                    }.setNegativeButton(getString(R.string.common_no)) { _, _ ->
                    }
            }.create()
    }

    private fun initSettingObserver() {
        settingViewModel.state.observe(this) {
            when (settingViewModel.state.value) {
                SettingState.Logout.InProgress -> logoutConfirmDialog.show()

                SettingState.Logout.Done -> LoginActivity.start(this)

                else -> {}
            }
        }
    }

    companion object {
        private const val LOGOUT_CONFIRM_MESSAGE = "정말 로그아웃하시겠습니까?"
    }
}
