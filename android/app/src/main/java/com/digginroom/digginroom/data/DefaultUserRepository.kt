package com.digginroom.digginroom.data

import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository

class DefaultUserRepository : UserRepository {

    override fun find(user: User): String {
        TODO("Not yet implemented")
    }

    override fun save(user: User): Result<String> {
        return Result.success("fakeToken")
    }
}
