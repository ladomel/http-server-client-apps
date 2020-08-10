package com.example.http_clinet_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class User (
    var id: String,
    var nickname: String,
    var description: String,
    var image: String?
) : Parcelable, Serializable