package com.example.http_clinet_app.login

import com.example.http_clinet_app.model.User

interface LoginContract {
    interface View {
        fun gotoChats(user: User)
        fun showMessage(text: String)
    }
    interface Presenter {
        fun login(user: User)
    }
}