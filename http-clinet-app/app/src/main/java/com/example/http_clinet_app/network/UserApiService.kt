package com.example.http_clinet_app.network

import com.example.http_clinet_app.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("user")
    fun loadUsers(): Call<List<User>>

    @GET("register")
    fun getUser(@Query("id") id: String): Call<User>

    @POST("register")
    fun saveUser(@Body body: User): Call<User>
}

object UserApi {
    val retrofitService: UserApiService by lazy {
        Base.getInstance().create(UserApiService::class.java)
    }
}