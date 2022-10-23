package com.teste.poc.coreapi.session

import com.teste.poc.application.domain.model.User

interface ISessionOutput {
    fun getUser(): User?

    fun getCode(): String
}