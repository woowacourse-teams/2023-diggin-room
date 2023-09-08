package com.digginroom.digginroom.model

data class AccountModel(
    var id: String = EMPTY_STRING,
    var password: String = EMPTY_STRING
) {

    companion object {
        private const val EMPTY_STRING = ""
    }
}
