package com.example.roombasicimpl.domain.use_case.contact


class ContactUseCases (
    val getContacts: GetAllContactsUseCase,
    val deleteContact: DeleteContactUseCase,
    val addContact: AddContactUseCase,
    val getContact: GetContactByIDUseCase,
    val updateContact: UpdateContactUseCase,
)

