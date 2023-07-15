package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.JoinResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/join")
    suspend fun save(
        @Query("id") id: String,
        @Query("password") password: String
    ): JoinResponse
}
