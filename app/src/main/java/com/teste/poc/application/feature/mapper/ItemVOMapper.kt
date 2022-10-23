package com.teste.poc.application.feature.mapper

import com.teste.poc.application.domain.model.User
import com.teste.poc.application.dto.Music
import com.teste.poc.application.feature.viewobject.ItemCardVO
import com.teste.poc.application.feature.viewobject.ItemVO

object ItemVOMapper {

    fun User.toItemVO() = ItemVO(
        loverName = nameLover,
        instagram = instagram,
        number = whatssap,
        imageProfile = myImage,
        spotify = spotify,
        imageBackground = backgoundImage,
        textPlus = plus,
        cardsVO = toItemCardVO()
    )

    fun User.toItemCardVO() = lovers.map {
        ItemCardVO(
            id = it.id,
            music = it.music,
            name = myName,
            image = it.image,
            description = it.textLover
        )
    }.toMutableList()

    fun User.toItemVOFilter(id: Int) = ItemVO(
        loverName = nameLover,
        instagram = instagram,
        number = whatssap,
        imageProfile = myImage,
        spotify = spotify,
        imageBackground = backgoundImage,
        textPlus = plus,
        cardsVO = toItemCardVOFilter(id)
    )

    fun User.toItemCardVOFilter(id: Int) = lovers.filterIndexed { index, pair ->
        pair.id == id
    }.map {
        ItemCardVO(
            id = it.id,
            music = it.music,
            name = myName,
            image = it.image,
            description = it.textLover
        )
    }.toMutableList()

}