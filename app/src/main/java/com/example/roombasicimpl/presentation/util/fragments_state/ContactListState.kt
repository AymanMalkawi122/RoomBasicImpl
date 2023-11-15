package com.example.roombasicimpl.presentation.util.fragments_state

import com.example.roombasicimpl.domain.model.Contact

data class ContactListState(
    var contactList: List<Contact> = emptyList(),
)
