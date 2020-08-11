package com.example.http_server_app.db

import androidx.room.*

@Dao
interface ChatDao {

    @Query("select distinct * from chats where fromUserId = :userId or toUserId = :userId order by lastMessageDate desc limit :limit offset :offset")
    fun loadChatsByUser(userId: String, limit: Int, offset: Int): List<Chat>

    @Query("select * from chats where id = :chatId")
    fun getChat(chatId: Int): Chat

    @Insert
    fun insertChat(chat: Chat): Long

    @Update
    fun updateChat(chat: Chat)

    @Delete
    fun deleteChat(chat: Chat)

    @Query("delete from chats where id = :id")
    fun deleteChat(id: Int)

    @Query("delete from chats")
    fun truncate()
}
