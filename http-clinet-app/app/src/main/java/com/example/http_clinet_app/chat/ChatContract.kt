package com.example.http_clinet_app.chat

import com.example.http_clinet_app.model.Chat
import com.example.http_clinet_app.model.User

interface ChatContract {
    interface View {
        fun showError(text: String)
        fun gotoMessagePage(chat: Chat)
        fun setData(data: List<Chat>)
        fun showUserDialog(users: List<User>)
    }
    interface Presenter {
        fun loadChats(userId: String)
        fun loadUsers()
        fun createChat(from: String, to: String)
    }
}