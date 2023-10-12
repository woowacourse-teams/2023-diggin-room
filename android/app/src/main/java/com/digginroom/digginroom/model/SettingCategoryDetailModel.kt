package com.digginroom.digginroom.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SettingCategoryDetailModel(
    @StringRes
    val description: Int,
    @DrawableRes
    val descriptionImg: Int,
    val onClick: (() -> Unit)? = null
)
