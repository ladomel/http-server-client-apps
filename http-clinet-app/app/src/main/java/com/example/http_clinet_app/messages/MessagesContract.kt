package com.example.http_clinet_app.messages

import com.example.http_clinet_app.model.Message

interface MessagesContract {
    interface View {
        fun showError(text: String)
        fun setData(data: List<Message>)
        fun refreshView(message: Message)
    }
    interface Presenter {
        fun loadMessages(chatId: Int)
        fun sendMessage(message: Message)
    }
}