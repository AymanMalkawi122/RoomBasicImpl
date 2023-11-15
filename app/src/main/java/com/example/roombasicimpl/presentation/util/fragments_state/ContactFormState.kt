package com.example.roombasicimpl.presentation.util.fragments_state

import com.example.roombasicimpl.domain.model.Contact


data class ContactFormState(
    var contactTransactionSuccessful:Boolean? = null,
    var contactFetchSuccessful:Boolean? = null,
    var contactInitialDetails: Contact? = null
)
