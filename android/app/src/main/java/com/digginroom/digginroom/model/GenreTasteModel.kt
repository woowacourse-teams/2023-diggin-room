package com.digginroom.digginroom.model

import androidx.annotation.DrawableRes

data class GenreTasteModel(
    val title: String,
    @DrawableRes
    val descriptionImageId: Int,
    val isSelected: Boolean
)
