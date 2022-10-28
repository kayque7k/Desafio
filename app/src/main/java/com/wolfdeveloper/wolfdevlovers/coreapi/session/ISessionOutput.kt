package com.wolfdeveloper.wolfdevlovers.coreapi.session

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import java.sql.Timestamp

interface ISessionOutput {
    fun getUser(): User?

    fun getCode(): String

    fun getDateFinal(): Timestamp

    fun isValidade(): Boolean
}