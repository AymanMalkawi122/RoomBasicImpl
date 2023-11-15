package com.example.roombasicimpl.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity( foreignKeys = [ForeignKey(
    entity = Contact::class,
    childColumns = ["contactId"],
    parentColumns = ["id"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)]
)
data class Call(
    val contactId: Int,
    val callDate: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) {
    val dateFormatted: String
        get() = callDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}

class InvalidCallException(message: String): Exception(message)


