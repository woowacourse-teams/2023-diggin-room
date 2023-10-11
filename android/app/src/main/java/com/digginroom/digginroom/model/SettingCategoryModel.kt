package com.digginroom.digginroom.model

import androidx.annotation.StringRes
import com.digginroom.digginroom.R

sealed class SettingCategoryModel(val details: List<SettingCategoryDetailModel>) {

    abstract val description: Int

    class Account(
        details: List<SettingCategoryDetailModel>
    ) : SettingCategoryModel(details) {

        @StringRes
        override val description: Int = R.string.common_account
    }

    class Etc(
        details: List<SettingCategoryDetailModel>
    ) : SettingCategoryModel(details) {

        @StringRes
        override val description: Int = R.string.common_etc
    }
}
