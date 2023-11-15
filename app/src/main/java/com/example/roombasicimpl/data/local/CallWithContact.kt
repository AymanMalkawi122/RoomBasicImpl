package com.example.roombasicimpl.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roombasicimpl.domain.model.Call
import com.example.roombasicimpl.domain.model.Contact


class CallWithContact {
    @Embedded
    var call: Call? = null

    @Relation(entity = Contact::class, parentColumn = "contact_id", entityColumn = "id")
    var contact: Contact? = null

}