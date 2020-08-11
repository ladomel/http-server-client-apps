package com.example.http_clinet_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatCreateModel (
    var from: String,
    var to: String,
    var text: String
) : Parcelable