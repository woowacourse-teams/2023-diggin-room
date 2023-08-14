package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.GoogleLoginRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.entity.IdDuplicationResponse
import com.digginroom.digginroom.data.entity.JoinRequest
import com.digginroom.digginroom.data.entity.LoginRequest
import com.digginroom.digginroom.data.service.AccountService
import com.digginroom.digginroom.model.user.LoginResult
import retrofit2.Response

class AccountRemoteDataSource(
    private val accountService: AccountService
) {

    suspend fun postJoin(id: String, password: String) {
        val response: Response<Void> = accountService.postJoin(
            JoinRequest(
                id = id,
                password = password
            )
        )

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() != 201) throw HttpError.Unknown(response)
    }

    suspend fun postLogin(id: String, password: String): LoginResult {
        val response = accountService.postLogin(
            LoginRequest(
                id = id,
                password = password
            )
        )

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() == 200) {
            return LoginResult(
                token = response.headers().get(SET_COOKIE)
                    ?: throw HttpError.EmptyBody(response),
                hasFavorite = response.body()?.hasFavorite
                    ?: throw HttpError.EmptyBody(response)
            )
        }

        throw HttpError.Unknown(response)
    }

    suspend fun postLogin(idToken: String): LoginResult {
        val response = accountService.postLogin(GoogleLoginRequest(idToken))

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() == 200) {
            return LoginResult(
                token = response.headers().get(SET_COOKIE)
                    ?: throw HttpError.EmptyBody(response),
                hasFavorite = response.body()?.hasFavorite
                    ?: throw HttpError.EmptyBody(response)
            )
        }

        throw HttpError.Unknown(response)
    }

    suspend fun fetchIsDuplicatedId(id: String): IdDuplicationResponse {
        val response: Response<IdDuplicationResponse> = accountService.fetchIsDuplicatedId(id)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }

    companion object {

        private const val SET_COOKIE = "Set-Cookie"
    }
}
