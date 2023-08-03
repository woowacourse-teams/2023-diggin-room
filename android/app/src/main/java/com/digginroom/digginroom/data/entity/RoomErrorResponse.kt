package com.digginroom.digginroom.data.entity

sealed class RoomErrorResponse(override val message: String) : Throwable(message) {

    data class NoSuchScrappedRooms(override val message: String = ERROR_MESSAGE_NO_SCRAPPED_ROOMS) :
        RoomErrorResponse(message)

    data class Default(override val message: String = ERROR_MESSAGE_DEFAULT) :
        RoomErrorResponse(message)

    companion object {

        private const val ERROR_MESSAGE_DEFAULT = "룸 정보를 받아올 수 없습니다."
        private const val ERROR_MESSAGE_NO_SCRAPPED_ROOMS = "스크랩된 룸 정보를 받아올 수 없습니다."
    }
}
