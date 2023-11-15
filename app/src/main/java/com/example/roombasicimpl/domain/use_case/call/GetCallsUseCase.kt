package com.example.roombasicimpl.domain.use_case.call

import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.repository.ContactRepsitory
import kotlinx.coroutines.flow.Flow

class GetCallsUseCase(private val repository: ContactRepsitory){
    operator fun invoke():Flow<List<Call>>  {
        return repository.getCalls()
    }
}