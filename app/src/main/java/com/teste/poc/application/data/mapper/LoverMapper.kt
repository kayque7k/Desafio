package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.response.LoverResponse
import com.teste.poc.application.data.response.UserResponse
import com.teste.poc.application.domain.model.Lover
import com.teste.poc.application.domain.model.User
import com.teste.poc.commons.extensions.EMPTY_STRING

object LoverMapper {
    fun List<LoverResponse>.toLoverList() = map {
        it.toLover()
    }

    fun LoverResponse.toLover() = Lover(
        id = id,
        image = image,
        textLover = textLover,
        music = music
    )
}