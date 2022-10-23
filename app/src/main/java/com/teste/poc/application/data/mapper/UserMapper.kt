package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.input.UserInput
import com.teste.poc.application.data.mapper.LoverMapper.toLoverInputList
import com.teste.poc.application.data.mapper.LoverMapper.toLoverList
import com.teste.poc.application.data.response.UserResponse
import com.teste.poc.application.domain.model.User

object UserMapper {
    fun UserResponse.toUser() = User(
        id = id,
        myName = myName,
        myImage = myImage,
        backgoundImage = backgoundImage,
        nameLover = nameLover,
        plus = plus,
        spotify = spotify,
        whatssap = whatssap,
        instagram = instagram,
        code = code,
        lovers = lovers.toLoverList()
    )

    fun User.toUserInput() = UserInput(
        id = id,
        myName = myName,
        myImage = myImage,
        backgoundImage = backgoundImage,
        nameLover = nameLover,
        plus = plus,
        spotify = spotify,
        whatssap = whatssap,
        instagram = instagram,
        code = code,
        lovers = lovers.toLoverInputList()
    )

}