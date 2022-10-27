package com.wolfdeveloper.wolfdevlovers.application.data.mapper

import com.wolfdeveloper.wolfdevlovers.application.data.input.UserInput
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.LoverMapper.toLoverInputList
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.LoverMapper.toLoverList
import com.wolfdeveloper.wolfdevlovers.application.data.response.UserResponse
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User

object UserMapper {
    fun UserResponse.toUser() = User(
        id = id,
        myName = myName,
        myImage = myImage,
        backgroundImage = backgroundImage,
        nameLover = nameLover,
        plus = plus,
        linkPlus = linkPlus,
        whatssap = whatssap,
        socialMediaLink = socialMediaLink,
        code = code,
        posts = lovers.toLoverList()
    )

    fun User.toUserInput() = UserInput(
        id = id,
        myName = myName,
        myImage = myImage,
        backgroundImage = backgroundImage,
        nameLover = nameLover,
        plus = plus,
        linkPlus = linkPlus,
        whatssap = whatssap,
        socialMediaLink = socialMediaLink,
        code = code,
        lovers = posts.toLoverInputList()
    )

}