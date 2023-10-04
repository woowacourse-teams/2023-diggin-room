package com.digginroom.digginroom.model

import androidx.annotation.StringRes
import com.digginroom.digginroom.R

interface SettingCategoryModel {

    val description: Int
    val details: List<SettingCategoryDetailModel>

    object Account : SettingCategoryModel {

        @StringRes
        override val description: Int = R.string.common_account

        override val details: List<SettingCategoryDetailModel> =
            listOf(
                SettingCategoryDetailModel(
                    description = R.string.common_login,
                    descriptionImg = R.drawable.ic_logout
                ),
                SettingCategoryDetailModel(
                    description = R.string.common_withdrawal,
                    descriptionImg = R.drawable.ic_withdrawal
                )
            )
    }

    object Etc : SettingCategoryModel {

        @StringRes
        override val description: Int = R.string.common_etc

        override val details: List<SettingCategoryDetailModel> =
            listOf(
                SettingCategoryDetailModel(
                    description = R.string.common_feedback,
                    descriptionImg = R.drawable.ic_logout
                )
            )
    }
}
