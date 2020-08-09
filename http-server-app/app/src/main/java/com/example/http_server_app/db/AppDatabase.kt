package com.example.http_server_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(AppTypeConverters::class)
@Database(entities = [User::class, Chat::class, Message::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getChatDao(): ChatDao
    abstract fun getMessageDao(): MessageDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context?): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context!!.applicationContext,
                        AppDatabase::class.java, "AppDatabase"
                    ).fallbackToDestructiveMigration() // for faster development
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}