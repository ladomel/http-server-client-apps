package com.example.http_clinet_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Chat (
    val id: Int,
    var fromUserId: String,
    var toUserId: String,
    var lastDate: Date
) : Parcelable