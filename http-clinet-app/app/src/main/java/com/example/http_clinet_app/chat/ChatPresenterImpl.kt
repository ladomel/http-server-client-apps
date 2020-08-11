package com.example.http_clinet_app.chat

import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.network.ChatApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatPresenterImpl(var view: ChatContract.View): ChatContract.Presenter {

    private var page: Int = 1
    private var limit: Int = 10

    override fun loadChats(userId: String) {
        ChatApi.retrofitService.loadChat(userId, page, limit).enqueue(object : Callback<List<Chat>> {
            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                response.body()?.let { view.setData(it) }
            }
        })
    }
}