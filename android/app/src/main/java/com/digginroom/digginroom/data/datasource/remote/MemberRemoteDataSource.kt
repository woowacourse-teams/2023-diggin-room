package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.data.entity.GenresTasteRequest
import com.digginroom.digginroom.data.entity.HttpError
import com.digginroom.digginroom.data.service.MemberService
import retrofit2.Response

class MemberRemoteDataSource(
    private val memberService: MemberService
) {

    fun postGenresTaste(genres: List<String>) {
        val response: Response<Void> = memberService.postGenresTaste(GenresTasteRequest(genres))

        if (response.code() == 400) throw HttpError.BadRequest(response)
        if (response.code() == 401) throw HttpError.Unauthorized(response)

        if (response.code() != 201) throw HttpError.Unknown(response)
    }
}
