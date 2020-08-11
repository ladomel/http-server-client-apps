package com.example.http_clinet_app.messages

import com.example.http_clinet_app.model.Message
import com.example.http_clinet_app.network.MessageApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesPresenterImpl(var view: MessagesContract.View): MessagesContract.Presenter {

    override fun loadMessages(chatId: Int) {
        MessageApi.retrofitService.loadMessages(chatId).enqueue(object : Callback<List<Message>> {
            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                view.showError("Couldn't load messages!")
            }

            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                response.body()?.let { view.setData(it) }
            }
        })
    }

    override fun sendMessage(message: Message) {
        MessageApi.retrofitService.createMessage(message).enqueue(object : Callback<Message> {
            override fun onFailure(call: Call<Message>, t: Throwable) {
                view.showError("Couldn't send message!")
            }

            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                response.body()?.let { view.refreshView(it) }
            }

        })
    }
}