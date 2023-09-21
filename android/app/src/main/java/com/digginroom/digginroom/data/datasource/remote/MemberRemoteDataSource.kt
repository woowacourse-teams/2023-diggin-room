package com.digginroom.digginroom.data.datasource.remote

import androidx.annotation.Keep
import com.digginroom.digginroom.data.di.UnAuthorized
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.entity.MemberResponse
import com.digginroom.digginroom.data.service.MemberService
import retrofit2.Response

class MemberRemoteDataSource @Keep constructor(
    @UnAuthorized private val memberService: MemberService
) {

    suspend fun fetch(token: String): MemberResponse {
        val response: Response<MemberResponse> = memberService.fetch(token)

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() == 200) return response.body() ?: throw HttpError.EmptyBody(response)

        throw HttpError.Unknown(response)
    }
}
