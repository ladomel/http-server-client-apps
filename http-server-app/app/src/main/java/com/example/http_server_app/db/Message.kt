package com.example.http_server_app.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "messages",
    foreignKeys = [
        ForeignKey(entity = Chat::class,
            parentColumns = ["id"],
            childColumns = ["chatId"]
        )
    ])
data class Message (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var chatId: Int,
    var userId: String,
    var text: String,
    var date: Date
) : Parcelable