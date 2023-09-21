package com.digginroom.digginroom

import com.digginroom.digginroom.data.datasource.local.TokenLocalDataSource
import com.digginroom.digginroom.data.datasource.remote.AccountRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.CommentRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.GenreTasteRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.MemberRemoteDataSource
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.data.di.Token
import com.digginroom.digginroom.data.di.UnAuthorized
import com.digginroom.digginroom.data.repository.DefaultAccountRepository
import com.digginroom.digginroom.data.repository.DefaultCommentRepository
import com.digginroom.digginroom.data.repository.DefaultGenreTasteRepository
import com.digginroom.digginroom.data.repository.DefaultMemberRepository
import com.digginroom.digginroom.data.repository.DefaultRoomRepository
import com.digginroom.digginroom.data.repository.DefaultTokenRepository
import com.digginroom.digginroom.data.repository.DefaultTutorialRepository
import com.digginroom.digginroom.data.service.AccountService
import com.digginroom.digginroom.data.service.CommentService
import com.digginroom.digginroom.data.service.GenreTasteService
import com.digginroom.digginroom.data.service.MemberService
import com.digginroom.digginroom.data.service.RoomService
import com.digginroom.digginroom.repository.AccountRepository
import com.digginroom.digginroom.repository.CommentRepository
import com.digginroom.digginroom.repository.GenreTasteRepository
import com.digginroom.digginroom.repository.MemberRepository
import com.digginroom.digginroom.repository.RoomRepository
import com.digginroom.digginroom.repository.TokenRepository
import com.digginroom.digginroom.repository.TutorialRepository
import com.dygames.androiddi.lifecycle.LifecycleWatcherApplication
import com.dygames.di.dependencies
import com.dygames.di.lifecycle
import com.dygames.di.provider
import com.dygames.di.qualifier
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kakao.sdk.common.KakaoSdk
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlin.reflect.typeOf

class DigginRoomApplication : LifecycleWatcherApplication(typeOf<DigginRoomApplication>()) {
    private val url = BuildConfig.SERVER_URL

    override fun onCreate() {
        dependencies {
            lifecycle<DigginRoomApplication> {
                provider {
                    Interceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder().addHeader(
                                "cookie",
                                inject<TokenLocalDataSource>().fetch()
                            ).build()
                        )
                    }
                }

                provider<OkHttpClient> {
                    OkHttpClient.Builder().addInterceptor(interceptor = inject()).build()
                }

                qualifier(Token()) {
                    provider<Retrofit> {
                        Retrofit.Builder().baseUrl(url).client(inject())
                            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
                            .build()
                    }

                    provider<RoomService> {
                        inject<Retrofit>().create(RoomService::class.java)
                    }
                    provider<CommentService> {
                        inject<Retrofit>().create(CommentService::class.java)
                    }
                    provider<GenreTasteService> {
                        inject<Retrofit>().create(GenreTasteService::class.java)
                    }
                    provider<RoomRemoteDataSource>(typeOf<RoomRemoteDataSource>())
                    provider<CommentRemoteDataSource>(typeOf<CommentRemoteDataSource>())
                    provider<GenreTasteRemoteDataSource>(typeOf<GenreTasteRemoteDataSource>())
                }

                qualifier(UnAuthorized()) {
                    provider<Retrofit> {
                        Retrofit.Builder().baseUrl(url)
                            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
                            .build()
                    }

                    provider<AccountService> {
                        inject<Retrofit>().create(AccountService::class.java)
                    }
                    provider<MemberService> {
                        inject<Retrofit>().create(MemberService::class.java)
                    }
                    provider<MemberRemoteDataSource>(typeOf<MemberRemoteDataSource>())
                    provider<AccountRemoteDataSource>(typeOf<AccountRemoteDataSource>())
                }

                provider<TokenRepository>(typeOf<DefaultTokenRepository>())

                provider<AccountRepository>(typeOf<DefaultAccountRepository>())

                provider<RoomRepository>(typeOf<DefaultRoomRepository>())

                provider<MemberRepository>(typeOf<DefaultMemberRepository>())

                provider<CommentRepository>(typeOf<DefaultCommentRepository>())

                provider<GenreTasteRepository>(typeOf<DefaultGenreTasteRepository>())

                provider<TutorialRepository>(typeOf<DefaultTutorialRepository>())
            }
        }

        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}
