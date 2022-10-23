package com.teste.poc.coreapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE: String = "http://192.168.0.81:8080/"
const val END_POINT: String = BASE

fun retrofit() = Retrofit.Builder()
    .baseUrl(END_POINT)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
