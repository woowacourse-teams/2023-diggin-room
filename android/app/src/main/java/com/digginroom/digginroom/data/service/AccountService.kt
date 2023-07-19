package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {

    @POST("/join")
    suspend fun saveAccount(
        @Query("id") id: String,
        @Query("password") password: String
    ): Response<Void>

    @POST("/login")
    suspend fun postLogin(
        @Query("id") id: String,
        @Query("password") password: String
    ): Response<Void>

    @POST("/join/checkMemberIdDuplication")
    suspend fun fetchIsDuplicatedId(
        @Query("memberId")
        memberId: String
    ): Response<IdDuplicationResponse>
}