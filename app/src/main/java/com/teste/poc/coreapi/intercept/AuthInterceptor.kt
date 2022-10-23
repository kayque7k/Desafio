package com.teste.poc.coreapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//const val BASE: String = "http://192.168.0.81:8080/"
const val BASE: String = "https://wolfdevlovers.herokuapp.com/"
const val END_POINT: String = BASE

const val FORM_DATA: String = "multipart/form-data"
const val DOCUMENT: String = "document"

const val TIME_OUT: Long = 5

fun retrofit() = Retrofit.Builder().apply {
    client(OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(TIME_OUT, TimeUnit.MINUTES)
            .build())
    baseUrl(END_POINT)
    addConverterFactory(GsonConverterFactory.create())
}.build()
