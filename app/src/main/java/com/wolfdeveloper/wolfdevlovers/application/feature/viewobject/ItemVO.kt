package com.wolfdeveloper.wolfdevlovers.application.feature.viewobject

import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING

class ItemVO(
    var name: String = EMPTY_STRING,
    var loverName: String = EMPTY_STRING,
    var socialMediaLink: String = EMPTY_STRING,
    var number: String = EMPTY_STRING,
    var imageProfile: String = EMPTY_STRING,
    var linkPlus: String = EMPTY_STRING,
    var imageBackground: String = EMPTY_STRING,
    var textPlus: String = EMPTY_STRING,
    var code: String = EMPTY_STRING,
    var cardsVO: MutableList<ItemCardVO> = mutableListOf()
)