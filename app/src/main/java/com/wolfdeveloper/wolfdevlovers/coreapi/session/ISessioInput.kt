package com.wolfdeveloper.wolfdevlovers.coreapi.session

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User

interface ISessioInput {
    fun setUser(user: User?)

    fun setCode(code: String)
}