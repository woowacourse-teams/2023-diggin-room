package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.GenresTasteRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.entity.MemberRequest
import com.digginroom.digginroom.data.entity.MemberResponse
import com.digginroom.digginroom.data.service.MemberService
import retrofit2.Response

class MemberRemoteDataSource(
    private val memberService: MemberService
) {

    suspend fun postGenresTaste(genres: List<String>) {
        val response: Response<Void> = memberService.postGenresTaste(GenresTasteRequest(genres))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 200) throw HttpError.Unknown(response)
    }

    suspend fun fetch(token: String): MemberResponse {
        val response: Response<MemberResponse> = memberService.fetch(MemberRequest(token))

        if (response.code() == 400) throw HttpError.BadRequest(response)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.Unknown(response)
    }
}
