package com.example.http_server_app.model

import android.os.Parcelable
import com.example.http_server_app.db.Message
import com.example.http_server_app.db.User
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ChatModel (
    val id: Int,
    var fromUser: User,
    var toUser: User,
    var message: Message?
) : Parcelable, Serializable