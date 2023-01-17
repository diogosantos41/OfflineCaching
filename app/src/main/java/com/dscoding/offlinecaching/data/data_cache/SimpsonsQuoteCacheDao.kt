package com.dscoding.offlinecaching.data.data_cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dscoding.offlinecaching.data.model.SimpsonsQuote
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpsonsQuoteCacheDao {

    @Query("SELECT * FROM ${SimpsonsQuote.TABLE_NAME}")
    fun getAllSimpsonQuotes(): Flow<List<SimpsonsQuote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimpsonQuotes(simpsonsQuotes: List<SimpsonsQuote>)

    @Query("DELETE FROM ${SimpsonsQuote.TABLE_NAME}")
    suspend fun deleteAllSimpsonQuotes()
}