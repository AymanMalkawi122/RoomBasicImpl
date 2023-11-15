package com.example.roombasicimpl.domain.use_case.call

import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.repository.ContactRepsitory

class DeleteCallUseCase(private val repository: ContactRepsitory){
    suspend operator fun invoke(call: Call)  {
        return repository.deleteCall(call)
    }
}