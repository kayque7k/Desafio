package com.teste.renner.coreapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE: String = "https://"
const val END_POINT: String = BASE

fun retrofit() = Retrofit.Builder()
    .baseUrl(END_POINT)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
