package com.example.http_server_app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("select * from messages")
    fun loadAllMessages(): List<Message>

    @Query("select * from messages where chatId = :chatId")
    fun loadMessages(chatId: Int): List<Message>

    @Query("select * from messages where chatId = :chatId order by date desc limit 1")
    fun getLastChatMessage(chatId: Int): Message

    @Insert
    fun insertMessage(msg: Message): Long

    @Query("delete from messages where chatId = :chatId")
    fun deleteMessages(chatId: Int)

    @Query("delete from messages")
    fun truncate()
}
