package com.example.roombasicimpl.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.roombasicimpl.domain.model.Contact
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDao {
    @Query("SELECT * FROM contact")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE id == :id")
    suspend fun getContact(id: Int): Contact

    @Upsert
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
}
