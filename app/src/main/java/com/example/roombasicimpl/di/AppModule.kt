package com.example.roombasicimpl.di

import android.app.Application
import androidx.room.Room
import com.example.roombasicimpl.data.local.ContactsDataBase
import com.example.roombasicimpl.data.repository.ContactsRepsitoryImpl
import com.example.roombasicimpl.domain.repository.ContactRepsitory
import com.example.roombasicimpl.domain.use_case.call.AddCallUseCase
import com.example.roombasicimpl.domain.use_case.call.CallUseCases
import com.example.roombasicimpl.domain.use_case.call.DeleteCallUseCase
import com.example.roombasicimpl.domain.use_case.call.GetCallsUseCase
import com.example.roombasicimpl.domain.use_case.contact.AddContactUseCase
import com.example.roombasicimpl.domain.use_case.contact.ContactUseCases
import com.example.roombasicimpl.domain.use_case.contact.DeleteContactUseCase
import com.example.roombasicimpl.domain.use_case.contact.GetAllContactsUseCase
import com.example.roombasicimpl.domain.use_case.contact.GetContactByIDUseCase
import com.example.roombasicimpl.domain.use_case.contact.UpdateContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactsDatabase(app: Application): ContactsDataBase {
        return Room.databaseBuilder(
            app,
            ContactsDataBase::class.java,
            ContactsDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactsRepository(db: ContactsDataBase): ContactRepsitory {
        return ContactsRepsitoryImpl(db.contactsDao(), db.callsDao())
    }

    @Provides
    @Singleton
    fun provideContactUseCases(repository: ContactRepsitory): ContactUseCases {
        return ContactUseCases(
            getContacts = GetAllContactsUseCase(repository),
            deleteContact = DeleteContactUseCase(repository),
            addContact = AddContactUseCase(repository),
            getContact = GetContactByIDUseCase(repository),
            updateContact = UpdateContactUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideCallUseCases(repository: ContactRepsitory): CallUseCases {
        return CallUseCases(
            getCalls = GetCallsUseCase(repository),
            deleteCall = DeleteCallUseCase(repository),
            addCall = AddCallUseCase(repository)
        )
    }
}