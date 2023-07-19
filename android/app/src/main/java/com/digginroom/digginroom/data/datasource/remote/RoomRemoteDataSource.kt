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
                "h_umSa0IOSs"
            ).random(),
            Song("", "", "", emptyList(), emptyList()),
            false
        )
    }
}
