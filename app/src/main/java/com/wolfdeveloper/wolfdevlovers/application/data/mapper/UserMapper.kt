package com.wolfdeveloper.wolfdevlovers.application.data.mapper

import com.wolfdeveloper.wolfdevlovers.application.data.input.UserInput
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.LoverMapper.toLoverInputList
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.LoverMapper.toLoverList
import com.wolfdeveloper.wolfdevlovers.application.data.response.UserResponse
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import java.sql.Timestamp
import java.text.SimpleDateFormat

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
        dateCreated = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00").parse(dateCreated).run { Timestamp(time) },
        dateLife = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00").parse(dateLife).run { Timestamp(time) },
        timeLife = timeLife,
        attempts = attempts,
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
        dateCreated = null,
        dateLife = null,
        timeLife = timeLife,
        attempts = attempts,
        lovers = posts.toLoverInputList()
    )

}