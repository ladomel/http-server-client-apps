package com.example.http_clinet_app.loading

import com.example.http_clinet_app.model.User

interface LoadingContract {
    interface View {
        fun showError(text: String)
        fun gotoLoginPage(user: User?)
    }
    interface Presenter {
        fun findServer(userId: String?)
    }
}