package com.teste.poc.application.feature.viewobject

import com.teste.poc.commons.extensions.EMPTY_STRING

class ItemVO(
    var loverName: String = EMPTY_STRING,
    var instagram: String = EMPTY_STRING,
    var number: String = EMPTY_STRING,
    var imageProfile: String = EMPTY_STRING,
    var spotify: String = EMPTY_STRING,
    var imageBackground: String = EMPTY_STRING,
    var textPlus: String = EMPTY_STRING,
    var cardsVO: MutableList<ItemCardVO> = mutableListOf()
)