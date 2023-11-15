package com.example.roombasicimpl.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roombasicimpl.domain.model.Contact
import com.example.roombasicimpl.domain.use_case.contact.ContactUseCases
import com.example.roombasicimpl.presentation.util.fragments_state.ContactFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactFormViewModel @Inject constructor(
    private val contactsUseCases: ContactUseCases
) : ViewModel() {
    private val TAG = "ContactFormViewModel"
    private val _state = MutableStateFlow(ContactFormState())
    val state = _state.asStateFlow()

    private fun inputIsValid(contact: Contact): Boolean {
        return contact.firstName.isNotBlank() and contact.phoneNumber.isNotBlank()
    }

    suspend fun addContact(contact: Contact) {
        if (!inputIsValid(contact)) {
            _state.value = ContactFormState(contactTransactionSuccessful = false)
            return
        }
            viewModelScope.launch {
                try {
                    contactsUseCases.addContact(contact)
                    _state.value = ContactFormState(contactTransactionSuccessful = true)

                }
                catch (err: Exception){
                    _state.value = ContactFormState(contactTransactionSuccessful = false)
                    Log.v(TAG, "message add contact ${err.toString()}")
                }


            }

    }

    suspend fun updateContact(contact: Contact) {
        if (!inputIsValid(contact)) {
            _state.value = ContactFormState(contactTransactionSuccessful = false)
            return
        }
        viewModelScope.launch {
            try {
                contactsUseCases.updateContact(contact)
                _state.value = ContactFormState(contactTransactionSuccessful = true)

            }
            catch (err: Exception){
                _state.value = ContactFormState(contactTransactionSuccessful = false)
                Log.v(TAG, "message add contact ${err.toString()}")
            }


        }

    }

    suspend fun getContactById(id: Int) {
        viewModelScope.launch {
            try {
                val contact = contactsUseCases.getContact(id)
                _state.value = ContactFormState(contactFetchSuccessful = true, contactInitialDetails =contact)
                Log.v(TAG, "contact $contact")
            }
            catch (err: Exception){
                _state.value = ContactFormState(contactFetchSuccessful = false)
                Log.v(TAG, "message get contact ${err.toString()}")
            }


        }


    }
}