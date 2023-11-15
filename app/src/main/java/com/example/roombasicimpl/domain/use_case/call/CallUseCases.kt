package com.example.roombasicimpl.domain.use_case.call

data class CallUseCases(
    val getCalls: GetCallsUseCase,
    val deleteCall: DeleteCallUseCase,
    val addCall: AddCallUseCase,
)
