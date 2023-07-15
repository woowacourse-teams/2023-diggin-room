package com.digginroom.repository

import com.digginroom.model.user.User

interface UserRepository {
    fun find(user: User): String
    suspend fun save(user: User): Result<String>
}
