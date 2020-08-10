package com.example.http_clinet_app.network

import retrofit2.Call
import retrofit2.http.GET

interface TestApiService {
    @GET("/")
    fun checkConnection(): Call<String>
}

object TestApi {
    val retrofitService: TestApiService by lazy {
        Base.getInstance().create(TestApiService::class.java)
    }
}