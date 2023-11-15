package com.example.roombasicimpl.domain.repository

import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepsitory {
    fun getContacts(): Flow<List<Contact>>

    suspend fun getContactById(id: Int): Contact?

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun updateContact(contact: Contact)

    fun getCalls(): Flow<List<Call>>

    suspend fun insertCall(call: Call)

    suspend fun deleteCall(call: Call)
}
