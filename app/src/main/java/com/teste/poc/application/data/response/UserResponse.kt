package com.teste.poc.application.data.response

import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.extensions.ZERO

class UserResponse(
    var id: Int = ZERO,
    var myName: String = EMPTY_STRING,
    var myImage: String = EMPTY_STRING,
    var backgoundImage: String = EMPTY_STRING,
    var nameLover: String = EMPTY_STRING,
    var plus: String = EMPTY_STRING,
    var spotify: String = EMPTY_STRING,
    var whatssap: String = EMPTY_STRING,
    var instagram: String = EMPTY_STRING,
    var code: String = EMPTY_STRING,
    var lovers: List<LoverResponse> = listOf()
)