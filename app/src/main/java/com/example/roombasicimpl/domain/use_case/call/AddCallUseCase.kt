package com.example.roombasicimpl.domain.use_case.call

import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.InvalidCallException
import com.example.roombasicimpl.domain.repository.ContactRepsitory

class AddCallUseCase(private val repository: ContactRepsitory) {

    @Throws(InvalidCallException::class)
    suspend operator fun invoke(call: Call) {

        if(! contactIsValid(call.contactId)) {
            throw InvalidCallException("TODO")
        }

        repository.insertCall(call)
    }

    fun contactIsValid(contactId: Int): Boolean {
        return true
        TODO("check if contact is valid")
    }
}