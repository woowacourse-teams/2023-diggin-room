package com.digginroom.repository

import com.digginroom.model.user.JoinState
import com.digginroom.model.user.User

interface UserRepository {
    fun find(user: User): String
    fun save(user: User): JoinState
}
