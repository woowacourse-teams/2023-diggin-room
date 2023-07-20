package com.digginroom.digginroom.data.di

import com.digginroom.digginroom.BuildConfig
import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.data.service.AccountService
import com.digginroom.digginroom.data.service.RoomService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

class RemoteDataSourceProvider {
    private val url = BuildConfig.SERVER_URL

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

    private val accountService: AccountService = retrofit.create(AccountService::class.java)
    private val roomService: RoomService = retrofit.create(RoomService::class.java)

    val accountDataSource = AccountRemoteDataSource(accountService)
    val roomRemoteDataSource = RoomRemoteDataSource(roomService)
}
