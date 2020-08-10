package com.example.http_clinet_app.network

import com.example.http_clinet_app.model.Chat
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatApiService {
    @GET("chat")
    fun loadChat(): Call<List<Chat>>

    @POST("chat")
    fun createChat(): Call<Chat>

    @DELETE("chat")
    fun deleteChat()
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        Base.getInstance().create(ChatApiService::class.java)
    }
}