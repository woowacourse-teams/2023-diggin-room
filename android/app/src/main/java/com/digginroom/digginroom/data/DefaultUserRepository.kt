package com.digginroom.digginroom.data

import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.source.DefaultUserRemoteDataSource
import com.digginroom.digginroom.data.source.UserRemoteDataSource
import com.digginroom.model.user.User
import com.digginroom.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val userRemoteDataSource: UserRemoteDataSource = DefaultUserRemoteDataSource(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    override fun find(user: User): String {
        TODO("Not yet implemented")
    }

    override suspend fun save(user: User): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                userRemoteDataSource.save(
                    JoinRequest(
                        id = user.id.value,
                        password = user.password.value
                    )
                ).token
            }
        }
}
