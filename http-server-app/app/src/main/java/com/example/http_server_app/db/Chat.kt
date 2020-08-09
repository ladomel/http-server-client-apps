package com.example.http_server_app.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "chats",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["fromUserId"]
        ),
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["toUserId"]
        )
    ])
data class Chat (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var fromUserId: String,
    var toUserId: String,
    var lastDate: Date
) : Parcelable