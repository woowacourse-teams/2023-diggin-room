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
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RemoteDataSourceProvider(tokenLocalDataSource: TokenLocalDataSource) {
    private val url = BuildConfig.SERVER_URL

    private val interceptor by lazy {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().addHeader(
                    "cookie",
                    tokenLocalDataSource.fetch()
                ).build()
            )
        }
    }

    private val tokenClient by lazy { OkHttpClient.Builder().addInterceptor(interceptor).build() }

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

    private val accountService: AccountService by lazy { retrofit.create(AccountService::class.java) }
    private val memberService: MemberService by lazy { retrofit.create(MemberService::class.java) }
    private val roomService: RoomService by lazy { tokenRetrofit.create(RoomService::class.java) }
    private val commentService: CommentService by lazy { tokenRetrofit.create(CommentService::class.java) }
    private val genreTasteService: GenreTasteService by lazy {
        tokenRetrofit.create(GenreTasteService::class.java)
    }

    val accountDataSource by lazy { AccountRemoteDataSource(accountService) }
    val roomRemoteDataSource by lazy { RoomRemoteDataSource(roomService) }
    val memberRemoteDataSource by lazy { MemberRemoteDataSource(memberService) }
    val commentRemoteDataSource by lazy { CommentRemoteDataSource(commentService) }
    val genreTasteRemoteDataSource by lazy { GenreTasteRemoteDataSource(genreTasteService) }
}
