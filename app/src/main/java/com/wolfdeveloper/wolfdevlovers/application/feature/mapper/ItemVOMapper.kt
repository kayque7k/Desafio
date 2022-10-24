package com.wolfdeveloper.wolfdevlovers.application.feature.mapper

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemCardVO
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemVO

object ItemVOMapper {

    fun User.toItemVO() = ItemVO(
        name = myName,
        loverName = nameLover,
        instagram = instagram,
        number = whatssap,
        imageProfile = myImage,
        spotify = spotify,
        imageBackground = backgoundImage,
        textPlus = plus,
        code = code,
        cardsVO = toItemCardVO()
    )

    fun User.toItemCardVO() = lovers.map {
        ItemCardVO(
            id = it.id,
            music = it.music,
            name = nameLover,
            image = it.image,
            description = it.textLover
        )
    }.toMutableList()

    fun User.toItemVOFilter(id: Int) = ItemVO(
        name = myName,
        loverName = nameLover,
        instagram = instagram,
        number = whatssap,
        imageProfile = myImage,
        spotify = spotify,
        imageBackground = backgoundImage,
        textPlus = plus,
        code = code,
        cardsVO = toItemCardVOFilter(id)
    )

    fun User.toItemCardVOFilter(id: Int) = lovers.filterIndexed { _, pair ->
        pair.id == id
    }.map {
        ItemCardVO(
            id = it.id,
            music = it.music,
            name = nameLover,
            image = it.image,
            description = it.textLover
        )
    }.toMutableList()

}