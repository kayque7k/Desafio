package com.teste.poc.application.feature.viewobject

import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.extensions.ZERO

class ItemCardVO(
    var id: Int = ZERO,
    var music: String = EMPTY_STRING,
    var image: String = EMPTY_STRING,
    var name: String = EMPTY_STRING,
    var description: String = EMPTY_STRING
)