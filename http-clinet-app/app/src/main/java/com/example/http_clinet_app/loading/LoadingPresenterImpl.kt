package com.example.http_clinet_app.loading

import com.example.http_clinet_app.model.User
import com.example.http_clinet_app.network.Base
import com.example.http_clinet_app.network.TestApi
import com.example.http_clinet_app.network.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadingPresenterImpl(var view: LoadingContract.View): LoadingContract.Presenter {

    private val PORT: Int = 8888

    override fun findServer(userId: String?) {
        // TODO - check all local ips
        Base.setBaseUrl("http://10.0.2.2:$PORT/")

        TestApi.retrofitService.checkConnection().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                view.showError("Can't connect to server!")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (userId != null) {
                    UserApi.retrofitService.getUser(userId).enqueue(object : Callback<User> {
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            view.gotoLoginPage(null)
                        }

                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            view.gotoLoginPage(response.body())
                        }
                    })

                } else {
                    view.gotoLoginPage(null)
                }
            }
        })
    }
}