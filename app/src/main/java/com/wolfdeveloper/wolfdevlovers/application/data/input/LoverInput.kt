package com.wolfdeveloper.wolfdevlovers.application.data.input

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO

class LoverInput(
    var id: Int = ZERO,
    var image: String = EMPTY_STRING,
    var textLover: String = EMPTY_STRING,
    var music: String = EMPTY_STRING
)