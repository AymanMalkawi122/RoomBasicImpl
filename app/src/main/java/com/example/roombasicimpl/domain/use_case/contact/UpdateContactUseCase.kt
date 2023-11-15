package com.example.roombasicimpl.domain.use_case.contact

import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.repository.ContactRepsitory

class UpdateContactUseCase(private val repository: ContactRepsitory){
    suspend operator fun invoke(contact: Contact)  {
        return repository.updateContact(contact)
    }
}