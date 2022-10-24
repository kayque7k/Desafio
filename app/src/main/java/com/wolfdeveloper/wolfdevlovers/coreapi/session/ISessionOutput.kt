package com.wolfdeveloper.wolfdevlovers.coreapi.session

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User

interface ISessionOutput {
    fun getUser(): User?

    fun getCode(): String
}