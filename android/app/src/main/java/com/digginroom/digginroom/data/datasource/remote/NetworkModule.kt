package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.service.AccountService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object NetworkModule {

    private const val URL = "https://ec2-43-200-158-138.ap-northeast-2.compute.amazonaws.com"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

    val accountService: AccountService = retrofit.create(AccountService::class.java)
}
