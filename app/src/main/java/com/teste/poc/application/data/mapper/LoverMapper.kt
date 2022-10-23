package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.input.LoverInput
import com.teste.poc.application.data.response.LoverResponse
import com.teste.poc.application.domain.model.Lover

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

    fun List<Lover>.toLoverInputList() = map {
        it.toLoverInput()
    }

    fun Lover.toLoverInput() = LoverInput(
        id = id,
        image = image,
        textLover = textLover,
        music = music
    )
}