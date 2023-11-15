package com.example.roombasicimpl.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roombasicimpl.common.SnackBbarMaker
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.use_case.call.CallUseCases
import com.example.roombasicimpl.domain.use_case.contact.ContactUseCases
import com.example.roombasicimpl.domain.use_case.contact.GetAllContactsUseCase
import com.example.roombasicimpl.presentation.util.fragments_state.ContactListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsListViewModel @Inject constructor(
    private val contactsUseCases: ContactUseCases,
    private val callUseCases: CallUseCases
): ViewModel() {
    private val _state = MutableStateFlow(ContactListState())
    val state= _state.asStateFlow()

    suspend fun addCall(call: Call){
        viewModelScope.launch {
            callUseCases.addCall(call)
        }
    }

    suspend fun getAllContacts(){
        viewModelScope.launch {
            contactsUseCases.getContacts().collect{list ->
                _state.value = ContactListState(contactList =  list)
            }
        }
    }
}