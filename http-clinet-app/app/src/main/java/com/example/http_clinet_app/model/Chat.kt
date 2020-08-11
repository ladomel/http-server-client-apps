package com.example.http_clinet_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat (
    val id: Int,
    var fromUser: User,
    var toUser: User,
    var message: Message?
) : Parcelable