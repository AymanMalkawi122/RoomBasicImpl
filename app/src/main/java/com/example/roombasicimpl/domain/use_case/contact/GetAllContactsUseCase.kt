package com.example.roombasicimpl.domain.use_case.contact

import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.repository.ContactRepsitory
import kotlinx.coroutines.flow.Flow

class GetAllContactsUseCase(private val repository: ContactRepsitory){
    operator fun invoke():Flow<List<Contact>>  {
        return repository.getContacts()
    }
}