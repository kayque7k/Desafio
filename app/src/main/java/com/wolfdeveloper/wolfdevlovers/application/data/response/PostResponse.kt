package com.wolfdeveloper.wolfdevlovers.application.data.response

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO
import java.sql.Timestamp

class PostResponse(
    var id: Int = ZERO,
    var image: String = EMPTY_STRING,
    var textPublication: String = EMPTY_STRING,
    var link: String = EMPTY_STRING,
    var dateCreated: Timestamp = Timestamp(System.currentTimeMillis())
)