package com.teste.poc.application.data.input

import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.extensions.ZERO

class LoverInput(
    var id: Int = ZERO,
    var image: String = EMPTY_STRING,
    var textLover: String = EMPTY_STRING,
    var music: String = EMPTY_STRING
)