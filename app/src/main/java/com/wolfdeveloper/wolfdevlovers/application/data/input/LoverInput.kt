package com.wolfdeveloper.wolfdevlovers.application.data.input

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO

class LoverInput(
    var id: Int = ZERO,
    var image: String = EMPTY_STRING,
    var textPublication: String = EMPTY_STRING,
    var link: String = EMPTY_STRING
)