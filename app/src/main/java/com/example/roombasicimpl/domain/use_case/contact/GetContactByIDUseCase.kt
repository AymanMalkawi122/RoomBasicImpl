package com.example.roombasicimpl.domain.use_case.contact

import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.repository.ContactRepsitory

class GetContactByIDUseCase(private val repository: ContactRepsitory){
    suspend operator fun invoke(id: Int):Contact?  {
        return repository.getContactById(id)
    }
}