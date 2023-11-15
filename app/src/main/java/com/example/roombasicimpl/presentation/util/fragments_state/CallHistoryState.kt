package com.example.roombasicimpl.presentation.util.fragments_state

import com.example.roombasicimpl.domain.model.Call

data class CallHistoryState(

    var callList: List<Call> = emptyList(),
    var showDateTag: Boolean = false,
)
