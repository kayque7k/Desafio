package com.wolfdeveloper.wolfdevlovers.application.data.mapper

import com.wolfdeveloper.wolfdevlovers.application.data.input.LoverInput
import com.wolfdeveloper.wolfdevlovers.application.data.response.PostResponse
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Post

object LoverMapper {
    fun List<PostResponse>.toLoverList() = map {
        it.toLover()
    }

    fun PostResponse.toLover() = Post(
        id = id,
        image = image,
        textPublication = textPublication,
        link = link
    )

    fun List<Post>.toLoverInputList() = map {
        it.toLoverInput()
    }

    fun Post.toLoverInput() = LoverInput(
        id = id,
        image = image,
        textPublication = textPublication,
        link = textPublication
    )
}