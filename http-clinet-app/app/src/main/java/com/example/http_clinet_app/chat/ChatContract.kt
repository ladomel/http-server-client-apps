package com.example.http_clinet_app.chat

import com.example.http_clinet_app.model.Chat

interface ChatContract {
    interface View {
        fun showError(text: String)
        fun gotoMessagePage(chat: Chat)
        fun setData(data: List<Chat>)
    }
    interface Presenter {
        fun loadChats(userId: String)
    }
}