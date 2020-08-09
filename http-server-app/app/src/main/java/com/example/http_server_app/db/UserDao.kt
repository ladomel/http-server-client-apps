package com.example.http_server_app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("select * from users")
    fun loadUsers(): List<User>

    @Query("select * from users where id = :id")
    fun getUser(id: String): User

    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User)
}
