package com.teste.poc.coreapi.session

import com.teste.poc.application.domain.model.User

interface ISessioInput {
    fun setUser(user: User?)

    fun setCode(code: String)
}