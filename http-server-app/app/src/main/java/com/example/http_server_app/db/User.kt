package com.example.http_server_app.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var nickname: String,
    var description: String,
    var image: String?
) : Parcelable, Serializable