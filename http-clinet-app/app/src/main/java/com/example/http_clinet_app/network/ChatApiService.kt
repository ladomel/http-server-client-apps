package com.example.http_clinet_app.network

import com.example.http_clinet_app.model.Chat
import retrofit2.Call
import retrofit2.http.*

interface ChatApiService {
    @GET("chat")
    fun loadChat(
        @Query("userId") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<List<Chat>>

    @POST("chat")
    fun createChat(@Body body: Chat): Call<Chat>

    @DELETE("chat")
    fun deleteChat()
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        Base.getInstance().create(ChatApiService::class.java)
    }
}