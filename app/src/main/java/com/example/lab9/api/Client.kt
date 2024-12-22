package com.example.lab9.api

import OMDbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val BASE_URL = "http://www.omdbapi.com/"

    val apiService: OMDbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OMDbApiService::class.java)
    }
}