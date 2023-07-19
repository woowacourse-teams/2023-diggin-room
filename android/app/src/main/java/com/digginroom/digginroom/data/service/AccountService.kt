package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.JoinResponse
import com.digginroom.digginroom.data.entity.LoginResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {

    @POST("/join")
    suspend fun saveAccount(
        @Query("id") id: String,
        @Query("password") password: String
    ): JoinResponse

    @POST("/login")
    suspend fun postAccount(
        @Query("id") id: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}
