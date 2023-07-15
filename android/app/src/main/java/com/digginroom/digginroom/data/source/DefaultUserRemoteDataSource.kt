package com.digginroom.digginroom.data.source

import com.digginroom.digginroom.data.NetworkModule
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.service.UserService

class DefaultUserRemoteDataSource(
    private val userService: UserService = NetworkModule.userService
) : UserRemoteDataSource {

    override suspend fun save(joinRequest: JoinRequest): JoinResponse =
        userService.save(
            id = joinRequest.id,
            password = joinRequest.password
        )
}
