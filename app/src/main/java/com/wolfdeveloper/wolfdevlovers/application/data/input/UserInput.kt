package com.wolfdeveloper.wolfdevlovers.application.data.input

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO

class UserInput(
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
    var lovers: List<LoverInput> = listOf()
)