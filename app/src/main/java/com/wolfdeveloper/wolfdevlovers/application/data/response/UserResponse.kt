package com.wolfdeveloper.wolfdevlovers.application.data.response

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO
import java.sql.Timestamp

class UserResponse(
    var id: Int = ZERO,
    var myName: String = EMPTY_STRING,
    var myImage: String = EMPTY_STRING,
    var backgroundImage: String = EMPTY_STRING,
    var nameLover: String = EMPTY_STRING,
    var plus: String = EMPTY_STRING,
    var linkPlus: String = EMPTY_STRING,
    var whatssap: String = EMPTY_STRING,
    var socialMediaLink: String = EMPTY_STRING,
    var code: String = EMPTY_STRING,
    var dateCreated: String = EMPTY_STRING,
    var dateLife: String = EMPTY_STRING,
    var timeLife: Int = ZERO,
    var attempts: Int = ZERO,
    var lovers: List<PostResponse> = listOf()
)