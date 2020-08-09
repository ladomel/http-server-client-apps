package com.example.http_server_app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChatDao {

    @Query("select * from chats where fromUserId = :userId or toUserId = :userId order by lastDate desc limit :limit offset :offset")
    fun loadChatsByUser(userId: String, limit: Int, offset: Int): List<Chat>

    @Insert
    fun insertChat(chat: Chat): Long

    @Update
    fun updateChat(chat: Chat)
}
