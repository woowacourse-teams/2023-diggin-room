package com.digginroom.digginroom.data.service

import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {

    @POST("/join")
    suspend fun postJoin(
        @Body joinRequest: JoinRequest
    ): Response<Void>

    @POST("/login")
    suspend fun postLogin(
        @Query("id") id: String,
        @Query("password") password: String
    ): Response<Void>

    @GET("/join/exist")
    suspend fun fetchIsDuplicatedId(
        @Query("username")
        id: String
    ): Response<IdDuplicationResponse>
}
