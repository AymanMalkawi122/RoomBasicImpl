package com.example.roombasicimpl.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roombasicimpl.domain.use_case.call.CallUseCases
import com.example.roombasicimpl.presentation.util.fragments_state.CallHistoryState
import com.example.roombasicimpl.presentation.util.fragments_state.ContactListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CallHistoryViewModel @Inject constructor(
    private val callUseCases: CallUseCases
): ViewModel() {
    private val _state = MutableStateFlow(CallHistoryState())
    val state= _state.asStateFlow()


    suspend fun getCalls(){
        viewModelScope.launch {
            callUseCases.getCalls().collect{list ->
                _state.value = CallHistoryState(callList =  list.asReversed())
            }
        }
    }
}