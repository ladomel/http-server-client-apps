package com.example.http_server_app.main

interface MainContract {

    interface View {

    }

    interface Presenter {
        fun isServerRunning(): Boolean
        fun startServer()
        fun stopServer()
    }
}