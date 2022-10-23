package com.teste.poc.application.dto

import com.teste.poc.commons.extensions.EMPTY_STRING

data class Music(
    var id: Int = 0,
    var music: String = EMPTY_STRING,
    var name: String = EMPTY_STRING
)
