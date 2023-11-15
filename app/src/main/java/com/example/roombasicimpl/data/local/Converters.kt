package com.example.roombasicimpl.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun stringToDate(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return date?.toString()
    }
}