package com.example.http_clinet_app.chat

import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.model.ChatCreateModel
import com.example.http_clinet_app.model.User
import com.example.http_clinet_app.network.ChatApi
import com.example.http_clinet_app.network.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatPresenterImpl(var view: ChatContract.View): ChatContract.Presenter {

    private var page: Int = 1
    private var limit: Int = 100

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

    override fun loadUsers() {
        UserApi.retrofitService.loadUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let { view.showUserDialog(it) }
            }

        })
    }

    override fun createChat(from: String, to: String) {
        ChatApi.retrofitService.createChat(ChatCreateModel(from, to, "Hi")).enqueue(object : Callback<Chat> {
            override fun onFailure(call: Call<Chat>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                response.body()?.let {
                    loadChats(from)
                    view.gotoMessagePage(it)
                }
            }

        })
    }
}