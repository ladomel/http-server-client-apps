package com.example.http_server_app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("select * from messages where chatId = :chatId")
    fun loadMessages(chatId: Int): List<Message>

    @Insert
    fun insertMessage(msg: Message): Long
}
