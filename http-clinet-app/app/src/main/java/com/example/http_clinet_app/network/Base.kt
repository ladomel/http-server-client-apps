package com.example.http_clinet_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Base {
    private var BASE_URL: String = "http://localhost:8888"
    private var INSTANCE: Retrofit? = null

    @JvmStatic
    fun getInstance(): Retrofit {
        synchronized(this) {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return INSTANCE!!
        }
    }

    @JvmStatic
    fun setBaseUrl(baseUrl: String) {
        synchronized(this) {
            BASE_URL = baseUrl
            INSTANCE = null
        }
    }
}