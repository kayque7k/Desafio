package com.wolfdeveloper.wolfdevlovers.application.feature.mapper

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemCardVO
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemVO

object ItemVOMapper {

    fun User.toItemVO() = ItemVO(
        name = myName,
        loverName = nameLover,
        socialMediaLink = socialMediaLink,
        number = whatssap,
        imageProfile = myImage,
        linkPlus = linkPlus,
        imageBackground = backgroundImage,
        textPlus = plus,
        code = code,
        cardsVO = toItemCardVO()
    )

    fun User.toItemCardVO() = posts.map {
        ItemCardVO(
            id = it.id,
            link = it.link,
            name = nameLover,
            image = it.image,
            description = it.textPublication
        )
    }.toMutableList()

    fun User.toItemVOFilter(id: Int) = ItemVO(
        name = myName,
        loverName = nameLover,
        socialMediaLink = socialMediaLink,
        number = whatssap,
        imageProfile = myImage,
        linkPlus = linkPlus,
        imageBackground = backgroundImage,
        textPlus = plus,
        code = code,
        cardsVO = toItemCardVOFilter(id)
    )

    fun User.toItemCardVOFilter(id: Int) = posts.filterIndexed { _, pair ->
        pair.id == id
    }.map {
        ItemCardVO(
            id = it.id,
            link = it.link,
            name = nameLover,
            image = it.image,
            description = it.textPublication
        )
    }.toMutableList()

}