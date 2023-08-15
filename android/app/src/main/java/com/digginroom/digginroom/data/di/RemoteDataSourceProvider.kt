package com.digginroom.digginroom.data.di

import com.digginroom.digginroom.BuildConfig
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.CommentRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.MemberRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.data.service.AccountService
import com.digginroom.digginroom.data.service.CommentService
import com.digginroom.digginroom.data.service.MemberService
import com.digginroom.digginroom.data.service.RoomService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RemoteDataSourceProvider(tokenLocalDataSource: TokenLocalDataSource) {
    private val url = BuildConfig.SERVER_URL

    private val interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder().addHeader(
                "cookie",
                tokenLocalDataSource.fetch()
            ).build()
        )
    }

    private val tokenClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val tokenRetrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(url).client(tokenClient)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

    private val accountService: AccountService = retrofit.create(AccountService::class.java)
    private val roomService: RoomService = tokenRetrofit.create(RoomService::class.java)
    private val memberService: MemberService = tokenRetrofit.create(MemberService::class.java)
    private val commentService: CommentService = retrofit.create(CommentService::class.java)

    val accountDataSource = AccountRemoteDataSource(accountService)
    val roomRemoteDataSource = RoomRemoteDataSource(roomService)
    val memberRemoteDataSource = MemberRemoteDataSource(memberService)
    val commentRemoteDataSource = CommentRemoteDataSource(commentService)
}
