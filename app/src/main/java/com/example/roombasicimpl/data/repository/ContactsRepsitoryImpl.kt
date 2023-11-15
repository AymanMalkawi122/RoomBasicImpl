package com.example.roombasicimpl.data.repository

import com.example.roombasicimpl.data.local.CallsDao
import com.example.roombasicimpl.data.local.ContactsDao
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.repository.ContactRepsitory
import kotlinx.coroutines.flow.Flow

class ContactsRepsitoryImpl(
    private val contactsDao: ContactsDao,
    private val callsDao: CallsDao,
): ContactRepsitory{

    override fun getContacts(): Flow<List<Contact>> {
        return contactsDao.getAllContacts()
    }

    override suspend fun getContactById(id: Int): Contact {
        return contactsDao.getContact(id)
    }

    override suspend fun insertContact(contact: Contact) {
        return contactsDao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        return contactsDao.deleteContact(contact)
    }

    override suspend fun updateContact(contact: Contact) {
        return contactsDao.insertContact(contact)
    }

    override fun getCalls(): Flow<List<Call>> {
        return callsDao.getAllCalls()
    }

    override suspend fun insertCall(call: Call) {
        return callsDao.insertCall(call)
    }

    override suspend fun deleteCall(call: Call) {
        return callsDao.deleteCall(call)
    }
}