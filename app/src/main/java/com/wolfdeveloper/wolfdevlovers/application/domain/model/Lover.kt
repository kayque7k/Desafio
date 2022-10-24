package com.wolfdeveloper.wolfdevlovers.application.domain.model

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO

data class Lover(
    var id: Int = ZERO,
    var image: String = EMPTY_STRING,
    var textLover: String = EMPTY_STRING,
    var music: String = EMPTY_STRING
)