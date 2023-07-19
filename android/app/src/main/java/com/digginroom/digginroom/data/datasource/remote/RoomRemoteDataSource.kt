package com.digginroom.digginroom.data.datasource.remote

import com.digginroom.digginroom.model.room.Room
import com.digginroom.digginroom.model.room.Song

class RoomRemoteDataSource {
    suspend fun findNext(): Room {
        return Room(
            listOf(
                "ucZl6vQ_8Uo",
                "J9DlrnNlgfE",
                "TUfLHYtCSKs",
                "uZuTCkwAmis",
                "eMLbHR9U94A",
                "L8r7E6DDofU",
                "HMKexUTsHAQ",
                "ShlWJX6se3Q",
                "l7slCtlcqlg",
                "h_umSa0IOSs",
                "Q2r8luTFKE4",
                "kcelgrGY1h8",
                "yzInC0lHIMM",
                "pT0t_DN6-fQ",
                "9dIyrgv6FGY",
                "RPTXSz_tCGQ",
                "I6Ps87iPNjQ",
                "KsxsJymq0hk"
            ).random(),
            Song("", "", "", emptyList(), emptyList()),
            false
        )
    }
}
