package com.example.http_clinet_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Message (
    val id: Int,
    var chatId: Int,
    var userId: String,
    var text: String,
    var date: Date
) : Parcelable