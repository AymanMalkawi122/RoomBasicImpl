package com.example.roombasicimpl.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class Contact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val date: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {

    val fullName: String
        get() = "$firstName $lastName"
    private val generalFormat: String
        get() = "EEEE',' MMMM dd"
    private val dateDetailFormat: String
        get() = "hh:mm a"


    val dateDetail: String
        get() = date.format(DateTimeFormatter.ofPattern(dateDetailFormat))

    val dateFormatted: String
        get() = date.format(DateTimeFormatter.ofPattern(generalFormat))
}

class InvalidContactException(message: String) : Exception(message)
