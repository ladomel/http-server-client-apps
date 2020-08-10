package com.example.http_clinet_app.network

import com.example.http_clinet_app.model.Message
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApiService {
    @GET("message")
    fun loadMessages(): Call<List<Message>>

    @POST("message")
    fun createMessage(): Call<Message>
}

object MessageApi {
    val retrofitService: MessageApiService by lazy {
        Base.getInstance().create(MessageApiService::class.java)
    }
}