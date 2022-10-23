package com.teste.poc.application.data.repository

import com.teste.poc.application.data.api.UserApi
import com.teste.poc.application.data.mapper.UserMapper.toUser
import com.teste.poc.application.domain.model.User
import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.coreapi.session.ISessionOutput
import com.teste.poc.coreapi.session.ISessioInput

class UserRepositoryImpl(
    private val productApi: UserApi,
    private val input: ISessionOutput,
    private val output: ISessioInput
) : UserRepository {

    override suspend fun get(code: String): User {
//        var user = input.getUser()
//        if (user.isNull()) {
//            user = productApi.get(code).toUser()
//            output.setUser(user)
//        }
//        return user!!
        return productApi.get(code).toUser()
    }
}