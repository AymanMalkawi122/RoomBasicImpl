package com.example.roombasicimpl.domain.use_case.contact

import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.model.InvalidContactException
import com.example.roombasicimpl.domain.repository.ContactRepsitory

class AddContactUseCase(private val repository: ContactRepsitory) {

    @Throws(InvalidContactException::class)
    suspend operator fun invoke(contact: Contact) {

        if(! contactIsValid(contact)) {
            throw InvalidContactException("TODO")
        }

        repository.insertContact(contact)
    }

    fun contactIsValid(contact: Contact): Boolean {
        return true
        TODO("check if contact already exists")
    }
}