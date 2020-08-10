package com.example.http_clinet_app.login

import com.example.http_clinet_app.model.User
import com.example.http_clinet_app.network.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginPresenterImpl(var view: LoginContract.View): LoginContract.Presenter {

    override fun login(user: User) {
        if (user.description.isEmpty() || user.nickname.isEmpty()) {
            view.showMessage("Please enter nickname & description!")
            return
        }

        if (user.id.isEmpty()) {
            user.id = UUID.randomUUID().toString()
            UserApi.retrofitService.saveUser(user).enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    view.showMessage("Can't save user!")
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    view.gotoChats(user)
                }
            })
        } else {
            view.gotoChats(user)
        }
    }
}