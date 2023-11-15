package com.example.roombasicimpl.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roombasicimpl.domain.model.Call
import kotlinx.coroutines.flow.Flow


@Dao
interface CallsDao {
    @Query("SELECT * FROM call")
    fun getAllCalls(): Flow<List<Call>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCall(call: Call)


    @Delete
    suspend fun deleteCall(call: Call)
}
