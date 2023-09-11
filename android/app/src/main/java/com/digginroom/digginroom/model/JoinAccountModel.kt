package com.digginroom.digginroom.model

class JoinAccountModel(
    var id: String = EMPTY_STRING,
    var password: String = EMPTY_STRING,
    var reInputPassword: String = EMPTY_STRING
) {

    companion object {
        private const val EMPTY_STRING = ""
    }
}
