package com.example.http_server_app.db

import androidx.room.TypeConverter
import java.util.*

class AppTypeConverters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
