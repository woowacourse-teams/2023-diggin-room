package com.digginroom.digginroom.model.user

data class LoginResult(
    val token: String,
    val hasFavorite: Boolean
)
