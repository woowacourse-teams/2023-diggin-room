package com.digginroom.digginroom.data.di

import com.digginroom.digginroom.BuildConfig
import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.CommentRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.GenreTasteRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.MemberRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.data.service.AccountService
import com.digginroom.digginroom.data.service.CommentService
import com.digginroom.digginroom.data.service.GenreTasteService
import com.digginroom.digginroom.data.service.MemberService
import com.digginroom.digginroom.data.service.RoomService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
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
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(url)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .build()
    }

    private val accountService: AccountService = retrofit.create(AccountService::class.java)
    private val memberService: MemberService = retrofit.create(MemberService::class.java)
    private val roomService: RoomService = tokenRetrofit.create(RoomService::class.java)
    private val commentService: CommentService = tokenRetrofit.create(CommentService::class.java)
    private val genreTasteService: GenreTasteService =
        tokenRetrofit.create(GenreTasteService::class.java)

    val accountDataSource = AccountRemoteDataSource(accountService)
    val roomRemoteDataSource = RoomRemoteDataSource(roomService)
    val memberRemoteDataSource = MemberRemoteDataSource(memberService)
    val commentRemoteDataSource = CommentRemoteDataSource(commentService)
    val genreTasteRemoteDataSource = GenreTasteRemoteDataSource(genreTasteService)
}
