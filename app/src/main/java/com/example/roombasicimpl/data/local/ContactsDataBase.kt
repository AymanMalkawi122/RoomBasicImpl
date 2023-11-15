package com.example.roombasicimpl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact

@Database(entities = [Contact::class , Call::class], version = 1)
@TypeConverters(Converters::class)
abstract class ContactsDataBase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao
    abstract fun callsDao():CallsDao

    companion object {
        val DATABASE_NAME = "contacts_db"
    }
}