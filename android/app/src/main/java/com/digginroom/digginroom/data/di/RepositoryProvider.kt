package com.digginroom.digginroom.data.di

import android.content.Context
import com.digginroom.digginroom.data.repository.DefaultAccountRepository
import com.digginroom.digginroom.data.repository.DefaultCommentRepository
import com.digginroom.digginroom.data.repository.DefaultMemberRepository
import com.digginroom.digginroom.data.repository.DefaultRoomRepository
import com.digginroom.digginroom.data.repository.DefaultTokenRepository

class RepositoryProvider(context: Context) {
    private val localDataSourceProvider = LocalDataSourceProvider(context)
    private val remoteDataSourceProvider =
        RemoteDataSourceProvider(localDataSourceProvider.tokenLocalDataSource)

    val accountRepository = DefaultAccountRepository(remoteDataSourceProvider.accountDataSource)
    val tokenRepository = DefaultTokenRepository(localDataSourceProvider.tokenLocalDataSource)
    val roomRepository = DefaultRoomRepository(remoteDataSourceProvider.roomRemoteDataSource)
    val memberRepository = DefaultMemberRepository(remoteDataSourceProvider.memberRemoteDataSource)
    val commentRepository = DefaultCommentRepository(remoteDataSourceProvider.commentRemoteDataSource)
}
